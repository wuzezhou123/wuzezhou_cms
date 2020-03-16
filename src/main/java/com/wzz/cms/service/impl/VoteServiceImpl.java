package com.wzz.cms.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wzz.cms.dao.VoteMapper;
import com.wzz.cms.domain.Vote;
import com.wzz.cms.service.VoteService;


@Service
public class VoteServiceImpl implements VoteService {
	@Resource
	private VoteMapper voteMapper;

	@Override
	public int insert(Vote vote) {
		return voteMapper.insert(vote);
	}

	@Override
	public List<Vote> selects(Integer articleId) {
		List<Vote> list = voteMapper.selects(articleId);
		
		return list;
	}

	@Override
	public Vote select(Vote vote) {
		// TODO Auto-generated method stub
		return voteMapper.select(vote);
	}

}
