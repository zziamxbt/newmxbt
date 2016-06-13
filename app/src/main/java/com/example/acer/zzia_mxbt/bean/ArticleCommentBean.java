package com.example.acer.zzia_mxbt.bean;

import java.io.Serializable;

public class ArticleCommentBean implements Serializable{
	int Uid;
	String head;
	String nickname;
	String content;
	String createtime;
	public ArticleCommentBean(){}
	public ArticleCommentBean(int Uid,String head, String nickname, String content,
			String createtime) {
		super();
		this.Uid=Uid;
		this.head = head;
		this.nickname = nickname;
		this.content = content;
		this.createtime = createtime;
	}
	public void setUid(int Uid){
		this.Uid=Uid;
	}
	public int getUid(){
		return Uid;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
	

}
