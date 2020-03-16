package com.wzz.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wzz.cms.dao.SlideMapper;
import com.wzz.cms.domain.Slide;
import com.wzz.cms.service.SlideService;

@Service
public class SlideServiceImpl implements SlideService {
	@Resource
	SlideMapper slideMapper;

	@Override
	public List<Slide> selects() {
		// TODO Auto-generated method stub
		return slideMapper.selects();
	}

	

}
