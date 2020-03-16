package com.wzz.cms.dao;

import java.util.List;

import com.wzz.cms.domain.Article;
import com.wzz.cms.domain.Comment;

public interface CommentMapper {
	
	/**
	 * 
	 * @Title: insert 
	 * @Description: 增加评论
	 * @param comment
	 * @return
	 * @return: int
	 */
	int insert(Comment comment);
	/**
	 * 
	 * @Title: selects 
	 * @Description: 根据文章查询文章评论
	 * @param article
	 * @return
	 * @return: List<Comment>
	 */
	List<Comment> selects(Article article);
	
	/**
	 * 
	 * @Title: selectsByCommentNum 
	 * @Description: 按照评论数量排序
	 * @return
	 * @return: List<Comment>
	 */
	List<Article> selectsByCommentNum();
  /**
   * 
   * @Title: updateArticle 
   * @Description: 让评论数增加1
   * @param articleId
   * @return
   * @return: int
   */
	int updateArticle(Integer articleId);
}
