package com.example.acer.zzia_mxbt.bean;

import java.io.Serializable;

public class vote_content implements Serializable{
		int Uid;
	    String vote_head;
	    String vote_name;
	    String vote_publishtime;
	    String vote_coverimg;
	    String vote_title;
	    String vote_num;
	    boolean flag;
	    int AWid;
public vote_content(){}
public vote_content(int uid, String vote_head, String vote_name,
		String vote_publishtime, String vote_coverimg, String vote_title,
		String vote_num, boolean flag,int AWid) {
	super();
	Uid = uid;
	this.vote_head = vote_head;
	this.vote_name = vote_name;
	this.vote_publishtime = vote_publishtime;
	this.vote_coverimg = vote_coverimg;
	this.vote_title = vote_title;
	this.vote_num = vote_num;
	this.flag = flag;
	this.AWid=AWid;
}
public int getUid() {
	return Uid;
}
public void setUid(int uid) {
	Uid = uid;
}
public String getVote_head() {
	return vote_head;
}
public void setVote_head(String vote_head) {
	this.vote_head = vote_head;
}
public String getVote_name() {
	return vote_name;
}
public void setVote_name(String vote_name) {
	this.vote_name = vote_name;
}
public String getVote_publishtime() {
	return vote_publishtime;
}
public void setVote_publishtime(String vote_publishtime) {
	this.vote_publishtime = vote_publishtime;
}
public String getVote_coverimg() {
	return vote_coverimg;
}
public void setVote_coverimg(String vote_coverimg) {
	this.vote_coverimg = vote_coverimg;
}
public String getVote_title() {
	return vote_title;
}
public void setVote_title(String vote_title) {
	this.vote_title = vote_title;
}
public String getVote_num() {
	return vote_num;
}
public void setVote_num(String vote_num) {
	this.vote_num = vote_num;
}
public boolean isFlag() {
	return flag;
}
public void setFlag(boolean flag) {
	this.flag = flag;
}

public int getAWid() {
	return AWid;
}
public void setAWid(int AWid) {
	this.AWid=AWid;
}  

}
