package com.wzz.cms.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.bw.common.utils.NumberUtil;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.wzz.cms.domain.Article;
import com.wzz.cms.domain.Category;
import com.wzz.cms.domain.Channel;
import com.wzz.cms.domain.Collect;
import com.wzz.cms.domain.Comment;
import com.wzz.cms.domain.ContentType;
import com.wzz.cms.domain.Slide;
import com.wzz.cms.domain.User;
import com.wzz.cms.domain.Vote;
import com.wzz.cms.service.ArticleService;
import com.wzz.cms.service.ChannelService;
import com.wzz.cms.service.CollectService;
import com.wzz.cms.service.CommentService;
import com.wzz.cms.service.SlideService;
import com.wzz.cms.service.VoteService;

/**
 * 
 * @ClassName: IndexController 
 * @Description: 绯荤粺棣栭〉鍏ュ彛
 * @author: charles
 * @date: 2020骞�3鏈�9鏃� 涓婂崍11:20:23
 */
@Controller
public class IndexController {
	
	@Resource
	private ChannelService channelService;
	
	@Resource
	private ArticleService articleService;
	
	@Resource
	private SlideService slideService;
	
	
	@Resource
	private CommentService commentService;
	
	@Resource
	private CollectService collectService;
	
	@Resource
	private VoteService voteService;
	
	@Resource
	private RedisTemplate rt;
	/**
	 * 
	 * @Title: index 
	 * @Description:杩涘叆棣栭〉
	 * @param model
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = {"","/","index"})
	public String index(Model model,Article article,@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "5")Integer pageSize) {
		article.setStatus(1);//鍙樉绀哄鏍歌繃鐨勬枃绔�
		article.setDeleted(0);//鍙樉绀烘湭鍒犻櫎
	    model.addAttribute("article", article);//灏佽鏌ヨ鏉′欢
	    
		//鏌ヨ宸︿晶鏍忕洰
		List<Channel> channels = channelService.selects();
		model.addAttribute("channels", channels);
		
		//濡傛灉鏍忕洰ID 涓嶄负绌哄垯鏌ユ煡鍏朵笅鎵�鏈夌殑鍒嗙被
		if(article.getChannelId()!=null) {
		List<Category> categorys = channelService.selectsByChannelId(article.getChannelId());
		model.addAttribute("categorys", categorys);
		}
		//濡傛灉鏍忕洰涓虹┖锛岃鏄庢病鏈夌偣鍑诲乏渚ф爮鐩紝鍒欓粯璁や负鐑偣鏂囩珷
		if(article.getChannelId()==null) {
			article.setHot(1);//
			//鏌ヨ杞挱鍥剧殑
			List<Slide> slides = slideService.selects();
			model.addAttribute("slides", slides);
		}
		List<Article> rtList = rt.opsForList().range("cms_hot",0,-1);
		//鏌ヨ鎵�鏈夌殑鏂囩珷
		if(rtList==null || rtList.size()==0) {
			PageInfo<Article> info = articleService.selects(article, page, pageSize);
			rt.opsForList().leftPushAll("cms_hot",info.getList());
			
			info.getList().forEach(System.out :: println);
			rt.expire("cms_hot",5, TimeUnit.MINUTES);
			model.addAttribute("info", info);
		}else {
			PageInfo<Article> info =new PageInfo<Article>(rtList);
			model.addAttribute("info", info);
		}
		
		
		
		
		
		//鍦ㄥ彸渚ф樉绀烘渶鏂�10閬嶆枃绔�
		Article article2 = new Article();
		article2.setStatus(1);//
		article2.setDeleted(0);
		PageInfo<Article> lastArticles = articleService.selects(article2, 1, 10);
		model.addAttribute("lastArticles", lastArticles);
		
		// 闂嵎璋冩煡
		Article voteArticle = new Article();
		voteArticle.setStatus(1);//
		voteArticle.setDeleted(0);
		voteArticle.setContentType(ContentType.VOTE);
		
		PageInfo<Article> voteArticles = articleService.selects(voteArticle, 1, 10);
		model.addAttribute("voteArticles", voteArticles);
		
		return "index/index";
		
	}
	/**
	 * 
	 * @Title: articleDetail 
	 * @Description: 鏂囩珷璇︽儏
	 * @param id
	 * @return
	 * @return: String
	 */
	@RequestMapping("articleDetail")
	public String articleDetail(HttpSession session ,Integer id,Model model,@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "5")Integer pageSize) {
		Article article = articleService.select(id);
		model.addAttribute("article", article);
		
		// 鏌ヨ鍑哄綋鍓嶆枃绔犵殑璇勮淇℃伅
		PageInfo<Comment> info = commentService.selects(article, page, pageSize);
		
		// 鏌ヨ鎵�鏈夋枃绔犵殑璇勮
		PageInfo<Article> info2 = commentService.selectsByCommentNum(1, 10);
		model.addAttribute("info", info);
		model.addAttribute("info2", info2);
		//鏌ヨ璇ユ枃绔犳槸鍚﹁鏀惰棌杩�
		
		
		User user = (User) session.getAttribute("user");
		  Collect collect =null;
		if(null !=user) {//濡傛灉鐢ㄦ埛宸茬粡鐧诲綍锛屽垯鏌ヨ鏄惁娌℃敹钘忚繃
		   collect = collectService.selectByTitleAndUserId(article.getTitle(), user.getId());
		}
		model.addAttribute("collect", collect);
		
		
		
		return "index/articleDetail";
	}

	
	/**
	 * 
	 * @Title: articleDetail 
	 * @Description: 鎶曠エ璇︽儏
	 * @param session
	 * @param id
	 * @param model
	 * @return
	 * @return: String
	 */
	@RequestMapping("voteDetail")
	public String voteDetail(HttpSession session ,Integer id,Model model) {
		Article article = articleService.select(id);
		
	String content = article.getContent();
		Gson gson =new Gson();
		LinkedHashMap<Character,String> mapVote = gson.fromJson(content, LinkedHashMap.class);
	
		
		model.addAttribute("mapVote", mapVote);
		model.addAttribute("article", article);
		
		//鏌ヨ鍑烘姇绁ㄦ儏鍐�
		
		List<Vote> votes = voteService.selects(article.getId());
		for (Vote vote : votes) {
			//鑾峰彇閫夐」鐨勫�煎苟閲嶆柊灏佽鍒皏ote
			vote.setOption(mapVote.get(vote.getOption()));
			//璁＄畻鐧惧垎姣�
			vote.setPercent(new BigDecimal(NumberUtil.ratio(vote.getOptionNum(), vote.getTotalNum())));
		}
		model.addAttribute("votes", votes);
		return "index/voteDetail";
		
	}
	
	
	//鎶曠エ
	@ResponseBody
	@PostMapping("addVote")
	public boolean addVote(Vote vote ,HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(null ==user)
		return false;//娌℃湁鐧诲綍鐨勭敤鎴蜂笉鑳芥敹钘�
		vote.setUserId(user.getId());
		//妫�鏌ョ敤鎴锋槸鍚﹀凡缁忔姇杩囩エ
		if(voteService.select(vote)!=null)
			return false;
	
		
		return voteService.insert(vote) >0;
	}
	
	
	//鏀惰棌鏂囩珷
		@ResponseBody
		@RequestMapping("deleteCollect")
		public boolean collect(Integer id) {
			return collectService.delete(id) >0;
		}
	
	
	
	//鏀惰棌鏂囩珷
	@ResponseBody
	@RequestMapping("collect")
	public boolean collect(Collect collect,HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(null ==user)
		return false;//娌℃湁鐧诲綍鐨勭敤鎴蜂笉鑳芥敹钘�
		collect.setUser(user);
		collect.setCreated(new Date());
		return collectService.insert(collect)>0;
	}
	
	//澧炲姞璇勮
	@ResponseBody
	@RequestMapping("addComment")
	public boolean addComment(Comment comment,Integer articleId,HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(null ==user)
		return false;//娌℃湁鐧诲綍鐨勭敤鎴蜂笉鑳借瘎璁�
		comment.setUserId(user.getId());
		comment.setArticleId(articleId);
		comment.setCreated(new Date());
		
		return commentService.insert(comment)>0;
	}
	@Autowired
	private ThreadPoolTaskExecutor executor;
	@ResponseBody
	@RequestMapping("dianJi")
	public boolean dianJi(Integer id,HttpSession session,HttpServletResponse response,HttpServletRequest request) throws UnsupportedEncodingException {
		//閫氳繃鏂囩珷id鏌ヨ鏂囩珷鏁版嵁
		Article article = articleService.select(id);
		String ip = request.getRemoteAddr();
		//濡傛灉娌℃湁cookie璇存槑娌℃湁鐐瑰嚮鍚屼竴绔�
		String redisKey="cms_"+id+"_"+ip;
		Boolean b = rt.hasKey(redisKey);
		if(b==true) {
			
			return false;
		}else {
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					rt.opsForValue().set(redisKey,"惊不惊喜");
				}
			});
			 Cookie ck1=new
					  Cookie(article.getId()+"",article.getId()+"");
					  ck1.setMaxAge(10000);
					  ck1.setPath("/");
					  response.addCookie(ck1);
					
					  articleService.dianJi(id);
		}
		 
		
		return true;
	}
}
