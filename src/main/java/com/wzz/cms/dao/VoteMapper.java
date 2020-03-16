package com.wzz.cms.dao;

import java.util.List;

import com.wzz.cms.domain.Vote;

/**
 * 
 * @ClassName: VoteMapper 
 * @Description: 投票
 * @author: charles
 * @date: 2020年3月15日 下午12:07:51
 */
public interface VoteMapper {

	int insert(Vote vote);
	/**
	 * 
	 * @Title: select 
	 * @Description: 查询某个文章的投票情况.一个文章可能被投票多次
	 * @param articleId
	 * @return
	 * @return: Vote
	 */
	List<Vote> selects(Integer articleId);
	
	/**
	 * 
	 * @Title: select 
	 * @Description:  查查用户是否重复投票
	 * @param vote
	 * @return
	 * @return: Vote
	 */
	Vote select(Vote vote);
}
