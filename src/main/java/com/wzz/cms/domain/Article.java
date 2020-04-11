package com.wzz.cms.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;








/**
 * 
 * @ClassName: Article 
 * @Description: 鏂囩珷鍐呭琛�
 * @author: charles
 * @date: 2020骞�3鏈�3鏃� 涓婂崍11:25:22
 */
	@Document(indexName = "wzz_rticle",type = "article") 
public class Article implements Serializable {

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;//涓婚敭
	@Field(index = true,analyzer = "ik_smart",store = true,searchAnalyzer = "ik_smart",type = FieldType.text)
	private String title;//鏂囩珷鏍囬
	private String summary;//鏂囩珷鎽樿
	private String content;//鏂囩珷鍐呭
	private String picture;//鏂囩珷鐨勬爣棰樺浘鐗�
	private Integer channelId;//鎵�灞炴爮鐩甀D
	private Integer categoryId;//鎵�灞炲垎绫籌D
	private Integer userId;//鏂囩珷鍙戝竷浜篒D
	private Integer hits;//  鐐瑰嚮閲�
	private Integer hot;//鏄惁鐑棬鏂囩珷 1锛氱儹闂� 0 锛氫竴鑸枃绔�
	private Integer status;//鏂囩珷瀹℃牳鐘舵��     0锛氬緟瀹�        1锛氬鏍搁�氳繃     -1: 瀹℃牳鏈�氳繃
	private Integer deleted;// 鍒犻櫎鐘舵�� 0:姝ｅ父锛�1锛氶�昏緫鍒犻櫎
	private Date created;// 鏂囩珷鍙戝竷鏃堕棿

	private  Date  updated;// 鏂囩珷淇敼鏃堕棿
	private Channel channel;
	private Category category;
	private User user;
	
	private String keywords;
	
	private String origin;
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	private ContentType contentType;//鏋氫妇绫诲瀷=鏂囩珷绫诲瀷    html:鏅�氭枃绔犵被鍨�  vote锛氭姇绁ㄧ被鍨�
	
	
	public ContentType getContentType() {
		return contentType;
	}
	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getHits() {
		return hits;
	}
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	public Integer getHot() {
		return hot;
	}
	public void setHot(Integer hot) {
		this.hot = hot;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", summary=" + summary + ", content=" + content + ", picture="
				+ picture + ", channelId=" + channelId + ", categoryId=" + categoryId + ", userId=" + userId + ", hits="
				+ hits + ", hot=" + hot + ", status=" + status + ", deleted=" + deleted + ", created=" + created
				+ ", updated=" + updated + ", channel=" + channel + ", category=" + category + ", user=" + user
				+ ", keywords=" + keywords + ", origin=" + origin + ", contentType=" + contentType + "]";
	}
	
}
