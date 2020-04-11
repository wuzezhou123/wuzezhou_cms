package com.wzz.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.wzz.cms.dao.ArticleMapper;
import com.wzz.cms.domain.Article;
import com.wzz.cms.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {
	@Resource
	private ArticleMapper articleMapper;

	@Resource
	private com.wzz.cms.repository.ArticleElasticSearchMapper aes;

	@Resource
	private RedisTemplate rt;

	@Override
	public int insert(Article article) {
		return articleMapper.insert(article);
	}

	@Override
	public PageInfo<Article> selects(Article article, Integer page, Integer pgeSize) {

		/*
		 * Iterable<Article> findAll = aes.findAll(); if (!findAll.iterator().hasNext())
		 * { List<Article> es = articleMapper.selects(article); aes.saveAll(es); }
		 */
		PageHelper.startPage(page, pgeSize);
		List<Article> list = articleMapper.selects(article);

		/*
		 * Pageable a=new PageRequest(page,pgeSize); Page<Article> findAll =
		 * aes.findAll(a);
		 */

		return new PageInfo<Article>(list);
	}

	@Override
	public Article select(Integer id) {
		// TODO Auto-generated method stub
		return articleMapper.select(id);
	}

	@Override
	public int update(Article article) {
		// 热点文章改变清空redis
		System.out.println("热点文章改变清空redis");
		rt.delete("cms_hot");
		return articleMapper.update(article);
	}

	@Override
	public int dianJi(Integer id) {

		return articleMapper.dianJi(id);
	}

}
