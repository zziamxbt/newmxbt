package com.example.acer.zzia_mxbt.bean;

import java.io.Serializable;

public class BeautyBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int Aid;//文章id
	private String Acoverimg;//文章封面
	private String Atitle;//文章标题
	private String Uhead;//作者头像
	private String Unickname;//作者昵称
	
	
	public BeautyBean(){
		
	}


	public BeautyBean(int aid, String acoverimg, String atitle, String uhead,
			String unickname) {
		super();
		Aid = aid;
		Acoverimg = acoverimg;
		Atitle = atitle;
		Uhead = uhead;
		Unickname = unickname;
	}


	public int getAid() {
		return Aid;
	}


	public void setAid(int aid) {
		Aid = aid;
	}


	public String getAcoverimg() {
		return Acoverimg;
	}


	public void setAcoverimg(String acoverimg) {
		Acoverimg = acoverimg;
	}


	public String getAtitle() {
		return Atitle;
	}


	public void setAtitle(String atitle) {
		Atitle = atitle;
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


	@Override
	public String toString() {
		return "BeautyBean [Aid=" + Aid + ", Acoverimg=" + Acoverimg
				+ ", Atitle=" + Atitle + ", Uhead=" + Uhead + ", Unickname="
				+ Unickname + "]";
	}
	
	
	
	
}
