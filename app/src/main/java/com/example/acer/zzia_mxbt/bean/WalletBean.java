package com.example.acer.zzia_mxbt.bean;

public class WalletBean {
	private int wbalance;
	private String userName;
	private String userSex;
	private String userHead;
	private String userBk;
	
	public WalletBean(){}
	
	public WalletBean(int wbalance, String userName, String userSex,
			String userHead, String userBk) {
		super();
		this.wbalance = wbalance;
		this.userName = userName;
		this.userSex = userSex;
		this.userHead = userHead;
		this.userBk = userBk;
	}
	public int getWbalance() {
		return wbalance;
	}
	public void setWbalance(int wbalance) {
		this.wbalance = wbalance;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserHead() {
		return userHead;
	}
	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}
	public String getUserBk() {
		return userBk;
	}
	public void setUserBk(String userBk) {
		this.userBk = userBk;
	}
	@Override
	public String toString() {
		return "WalletBean [wbalance=" + wbalance + ", userName=" + userName
				+ ", userSex=" + userSex + ", userHead=" + userHead
				+ ", userBk=" + userBk + "]";
	}
	
	
	

}
