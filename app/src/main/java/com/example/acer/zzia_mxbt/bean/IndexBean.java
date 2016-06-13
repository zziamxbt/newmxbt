package com.example.acer.zzia_mxbt.bean;

import java.io.Serializable;

public class IndexBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int articleId;
	String headImg;
	String nickName;
	String dateTime;
	String kind;
	String backGround;
	String title;
	String content;
	public int  getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getBackGround() {
		return backGround;
	}
	public void setBackGround(String backGround) {
		this.backGround = backGround;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "IndexBean [articleId="+articleId+",headImg=" + headImg + ", nickName=" + nickName
				+ ", dateTime=" + dateTime + ", kind=" + kind + ", backGround="
				+ backGround + ", title=" + title + ", content=" + content
				+ "]";
	}
	public IndexBean(int articleId ,String headImg, String nickName, String dateTime,
					 String kind, String backGround, String title, String content) {
		super();
		this.headImg = headImg;
		this.nickName = nickName;
		this.dateTime = dateTime;
		this.kind = kind;
		this.backGround = backGround;
		this.title = title;
		this.content = content;
		this.articleId = articleId;
	}
	public IndexBean(){
	}
}
