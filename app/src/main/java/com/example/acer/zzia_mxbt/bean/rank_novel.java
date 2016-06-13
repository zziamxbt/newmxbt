package com.example.acer.zzia_mxbt.bean;

import java.io.Serializable;

public class rank_novel implements Serializable{
	String head;
	String nickname;
	String updatetime;
	String isfinish;
	String coverimg;
	String title;
	int Aid;
	public rank_novel(){}
	public rank_novel(String head, String nickname, String updatetime,
			String isfinish, String coverimg, String title,int Aid) {
		super();
		this.head = head;
		this.nickname = nickname;
		this.updatetime = updatetime;
		this.isfinish = isfinish;
		this.coverimg = coverimg;
		this.title = title;
		this.Aid=Aid;
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
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getIsfinish() {
		return isfinish;
	}
	public void setIsfinish(String isfinish) {
		this.isfinish = isfinish;
	}
	public String getCoverimg() {
		return coverimg;
	}
	public void setCoverimg(String coverimg) {
		this.coverimg = coverimg;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setAid(int Aid){
		this.Aid=Aid;
	}
	public int getAid(){
		return Aid;
	}
}
