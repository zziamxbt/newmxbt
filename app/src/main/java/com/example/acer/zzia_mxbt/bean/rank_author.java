package com.example.acer.zzia_mxbt.bean;

import java.io.Serializable;

public class rank_author implements Serializable{
	String Uhead;
	String Unickname;
	String Ucontent;
	public rank_author(){}
	public rank_author(String uhead, String unickname, String ucontent) {
		super();
		Uhead = uhead;
		Unickname = unickname;
		Ucontent = ucontent;
	}
	public String getUhead() {
		return Uhead;
	}
	public void setUhead(String uhead) {
		Uhead = uhead;
	}
	public String getUnickname() {
		return Unickname;
	}
	public void setUnickname(String unickname) {
		Unickname = unickname;
	}
	public String getUcontent() {
		return Ucontent;
	}
	public void setUcontent(String ucontent) {
		Ucontent = ucontent;
	}
	
	
}
