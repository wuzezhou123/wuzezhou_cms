package com.wzz.cms.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.wzz.cms.domain.Article;
import com.wzz.cms.domain.ContentType;
import com.wzz.cms.domain.User;
import com.wzz.cms.repository.ArticleElasticSearchMapper;
import com.wzz.cms.service.ArticleService;
import com.wzz.cms.util.HLUtils;

/**
 * 
 * @ClassName: MyController
 * @Description: 涓汉涓績
 * @author: charles
 * @date: 2020骞�3鏈�4鏃� 涓婂崍10:50:25
 */
@RequestMapping("my")
@Controller
public class MyController {

	@Resource
	private ArticleService articleService;
	@SuppressWarnings("rawtypes")
	@Resource
	private RedisTemplate rt;

	@Resource
	private ArticleElasticSearchMapper aes;
	
	@Resource
	private ElasticsearchTemplate et;
	/**
	 * 
	 * @Title: index
	 * @Description: 杩涘叆涓汉涓績鐨勯椤�
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = { "", "/", "index" })
	public String index() {

		return "my/index";

	}

	/**
	 * 
	 * @Title: publishVote
	 * @Description: 鍘诲彂璧锋姇绁�
	 * @return
	 * @return: boolean
	 */
	@GetMapping("publishVote")
	public String publishVote() {
		return "/my/vote";

	}

	/**
	 * 
	 * @Title: publishVote
	 * @Description: 鍙戣捣鎶曠エ
	 * @return
	 * @return: boolean
	 */
	@ResponseBody
	@PostMapping("publishVote")
	public boolean publishVote(String[] options, Article article, HttpSession session) {
		// LinkedHashMap 鏄湁椤哄簭鐨勶紝
		LinkedHashMap<Character, String> map = new LinkedHashMap<Character, String>();
		char x = 'A';// 閫夐」浠� ABCD绛変緷娆℃帓搴忚繘琛�
		for (String option : options) {
			map.put(x, option);
			x = (char) (x + 1);
		}
		// 鎶妋ap鏁版嵁杞负json
		Gson gson = new Gson();
		String json = gson.toJson(map);

		article.setContent(json);// 鎶妀son鏁版嵁瀛樺叆鍐呭瀛楁
		article.setContentType(ContentType.VOTE);

		// 鏂囩珷鍒濆鏁版嵁
		User user = (User) session.getAttribute("user");
		article.setUserId(user.getId());// 鍙戝竷浜�
		article.setCreated(new Date());
		article.setHits(0);// 鐐瑰嚮閲忛粯璁� 0
		article.setDeleted(0);// 榛樿鏈垹闄�
		article.setHot(0);// 榛樿闈炵儹闂�
		article.setStatus(1);// 榛樿宸插鏍� ---- 涓昏鏄祴璇� 鏁版嵁

		return articleService.insert(article) > 0;

	}

	/**
	 * 
	 * @Title: articleDetail
	 * @Description: 鍗曚釜鏂囩珷涔堝唴瀹�
	 * @param id
	 * @return
	 * @return: Article
	 */
	@ResponseBody
	@RequestMapping("articleDetail")
	public Article articleDetail(Integer id) {

		return articleService.select(id);

	}

	/**
	 * 
	 * @Title: articles
	 * @Description: 鎴戠殑鏂囩珷
	 * @return
	 * @return: String
	 */
	@RequestMapping("articles")
	public String articles(Model model, HttpSession session, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "3") Integer pageSize) {
		Article article = new Article();
		User user = (User) session.getAttribute("user");// 浠巗ession鑾峰彇褰撳墠鐧诲綍浜虹殑淇℃伅
		System.out.println(user);
		article.setUserId(user.getId());// 鍙樉绀哄綋鍓嶇櫥褰曠殑浜烘枃绔�
		PageInfo<Article> info = articleService.selects(article, page, pageSize);

		model.addAttribute("info", info);
		return "my/articles";

	}

	/**
	 * 
	 * @Title: publish
	 * @Description: 鍘诲彂甯冩枃绔�
	 * @return
	 * @return: String
	 */
	@GetMapping("publish")
	public String publish() {
		return "my/publish";

	}

	/**
	 * 
	 * @Title: publish
	 * @Description: 鍙戝竷鏂囩珷
	 * @param file
	 * @param article
	 * @return
	 * @return: boolean
	 */
	@ResponseBody
	@PostMapping("publish")
	public boolean publish(MultipartFile file, Article article, HttpSession session) {
		// 鏂囦欢涓婁紶
		if (null != file && !file.isEmpty()) {
			String path = "d:/pic/";

			// 鏂囦欢鐨勫師濮嬪悕绉� 1.jpg
			String filename = file.getOriginalFilename();
			// 涓轰簡闃叉鏂囦欢閲嶅悕锛岄渶瑕佹敼鍙樻枃浠剁殑鍚嶅瓧
			String newFilename = UUID.randomUUID() + filename.substring(filename.lastIndexOf("."));

			File f = new File(path, newFilename);

			// 鎶婃枃浠跺啓鍏ョ‖鐩�
			try {
				file.transferTo(f);
				article.setPicture(newFilename);// 鏂囦欢鐨勫悕绉�
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		// 鏂囩珷鍒濆鏁版嵁
		User user = (User) session.getAttribute("user");
		article.setUserId(user.getId());// 鍙戝竷浜�
		article.setCreated(new Date());
		article.setHits(0);// 鐐瑰嚮閲忛粯璁� 0
		article.setDeleted(0);// 榛樿鏈垹闄�
		article.setHot(0);// 榛樿闈炵儹闂�
		article.setStatus(0);// 榛樿寰呭鏍�
		return articleService.insert(article) > 0;// 澧炲姞鏂囩珷

	}

	@RequestMapping("config")
	public String config(Model model,Article article,@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "5")Integer pageSize) {
		System.out.println(article.getTitle());
		PageInfo<Article> info = (PageInfo<Article>) HLUtils.findByHighLight(et, Article.class,pageNum,pageSize,new String[] {"title"},"id",article.getTitle());
		System.out.println(info.getList().get(0).getTitle());

		
		/*
		 * Iterable<Article> findAll = aes.findAll();
		 * 
		 * List<Article> list = new ArrayList<Article>(); findAll.forEach(new
		 * Consumer<Article>() {
		 * 
		 * @Override public void accept(Article t) {
		 * 
		 * list.add(t); } });
		 * 
		 * 
		 * User object = (User) rt.opsForValue().get("cms"); model.addAttribute("u",
		 * object); 
		 */
		
		model.addAttribute("info", info);
		model.addAttribute("article",article);
		return "my/myConfig";
	}

}
