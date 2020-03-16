package com.wzz.cms.service.impl;

import java.awt.color.CMMException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bw.common.utils.StringUtil;
import com.wzz.cms.dao.CollectMapper;
import com.wzz.cms.domain.Collect;
import com.wzz.cms.service.CollectService;

@Service
public class CollectServiceImpl  implements CollectService{
	@Resource
	CollectMapper collectMapper;

	@Override
	public int insert(Collect collect) {
		/*
		 * if(!StringUtil.isHttpUrl(collect.getUrl())) throw new
		 * CMMException("不是合法的url");
		 */
		return collectMapper.insert(collect);
	}

	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return collectMapper.delete(id);
	}

	@Override
	public List<Collect> selects(Integer userId) {
		// TODO Auto-generated method stub
		return collectMapper.selects(userId);
	}

	@Override
	public Collect selectByTitleAndUserId(String title, Integer userId) {
		// TODO Auto-generated method stub
		return collectMapper.selectByTitleAndUserId(title, userId);
	}

}
