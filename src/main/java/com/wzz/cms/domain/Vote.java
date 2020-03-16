package com.wzz.cms.domain;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 
 * @ClassName: Vote 
 * @Description: 投票
 * @author: charles
 * @date: 2020年3月15日 上午11:44:11
 */
public class Vote implements Serializable {
	
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
	private  Integer id;
	private  Integer articleId;
	private Integer userId;
	private String option;//投票选项  A B C D
	
	private Integer optionNum;//单票票数
	private Integer totalNum;//投票总票数
	
	private BigDecimal percent;//单票占百分比
	
	
	
	public BigDecimal getPercent() {
		return percent;
	}
	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}
	public Integer getOptionNum() {
		return optionNum;
	}
	public void setOptionNum(Integer optionNum) {
		this.optionNum = optionNum;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	@Override
	public String toString() {
		return "Vote [id=" + id + ", articleId=" + articleId + ", userId=" + userId + ", option=" + option
				+ ", optionNum=" + optionNum + ", totalNum=" + totalNum + ", percent=" + percent + "]";
	}
	

}
