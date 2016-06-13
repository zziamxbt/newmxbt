package com.example.acer.zzia_mxbt.bean;

public class LableBean {
	/**
	 * 对应lable表和image表,article_lable表
	 */
	private static final long serialVersionUID = 1L;
	
	private int Lid;//标签id
	private int LIid;// 标签图片id
	private String path;//图片id对应的地址
	private String Lcontent;//标签内容

	public LableBean() {
		// TODO Auto-generated constructor stub
	}


	public LableBean(int lid, int LIid, String path, String lcontent) {
		Lid = lid;
		this.LIid = LIid;
		this.path = path;
		Lcontent = lcontent;
	}

	public int getLid() {
		return Lid;
	}

	public void setLid(int lid) {
		Lid = lid;
	}

	public int getLIid() {
		return LIid;
	}

	public void setLIid(int LIid) {
		this.LIid = LIid;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLcontent() {
		return Lcontent;
	}

	public void setLcontent(String lcontent) {
		Lcontent = lcontent;
	}

	@Override
	public String toString() {
		return "LableBean{" +
				"Lid=" + Lid +
				", LIid=" + LIid +
				", path='" + path + '\'' +
				", Lcontent='" + Lcontent + '\'' +
				'}';
	}
}
