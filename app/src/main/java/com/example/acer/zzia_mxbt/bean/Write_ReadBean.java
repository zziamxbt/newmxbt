package com.example.acer.zzia_mxbt.bean;

import java.io.Serializable;

public class Write_ReadBean implements Serializable {
	
	private String write_articleName;
	private String write_ChapterName;
	private String write_ChapterContent;
	private String write_ChapterAuthor;
	private String write_ChapterAuthorName;
	private String write_ArticleTime;
	private boolean voteFlag;
	private boolean focusFlag;
	
	public Write_ReadBean(){}
	
	public Write_ReadBean(String write_articleName, String write_ChapterName,
			String write_ChapterContent, String write_ChapterAuthor,
			String write_ChapterAuthorName, String write_ArticleTime,boolean voteFlag,boolean focusFlag) {
		super();
		this.write_articleName = write_articleName;
		this.write_ChapterName = write_ChapterName;
		this.write_ChapterContent = write_ChapterContent;
		this.write_ChapterAuthor = write_ChapterAuthor;
		this.write_ChapterAuthorName = write_ChapterAuthorName;
		this.write_ArticleTime = write_ArticleTime;
		this.voteFlag=voteFlag;
		this.focusFlag=focusFlag;
	}
	
	
	public boolean isVoteFlag() {
		return voteFlag;
	}

	public void setVoteFlag(boolean voteFlag) {
		this.voteFlag = voteFlag;
	}

	public boolean isfocusFlag() {
		return focusFlag;
	}

	public void setfocusFlag(boolean focusFlag) {
		this.focusFlag = focusFlag;
	}

	public String getWrite_articleName() {
		return write_articleName;
	}
	public void setWrite_articleName(String write_articleName) {
		this.write_articleName = write_articleName;
	}
	public String getWrite_ChapterName() {
		return write_ChapterName;
	}
	public void setWrite_ChapterName(String write_ChapterName) {
		this.write_ChapterName = write_ChapterName;
	}
	public String getWrite_ChapterContent() {
		return write_ChapterContent;
	}
	public void setWrite_ChapterContent(String write_ChapterContent) {
		this.write_ChapterContent = write_ChapterContent;
	}
	public String getWrite_ChapterAuthor() {
		return write_ChapterAuthor;
	}
	public void setWrite_ChapterAuthor(String write_ChapterAuthor) {
		this.write_ChapterAuthor = write_ChapterAuthor;
	}
	public String getWrite_ChapterAuthorName() {
		return write_ChapterAuthorName;
	}
	public void setWrite_ChapterAuthorName(String write_ChapterAuthorName) {
		this.write_ChapterAuthorName = write_ChapterAuthorName;
	}
	public String getWrite_ArticleTime() {
		return write_ArticleTime;
	}
	public void setWrite_ArticleTime(String write_ArticleTime) {
		this.write_ArticleTime = write_ArticleTime;
	}
	@Override
	public String toString() {
		return "Write_ReadBean [write_articleName=" + write_articleName
				+ ", write_ChapterName=" + write_ChapterName
				+ ", write_ChapterContent=" + write_ChapterContent
				+ ", write_ChapterAuthor=" + write_ChapterAuthor
				+ ", write_ChapterAuthorName=" + write_ChapterAuthorName
				+ ", voteFlag=" + voteFlag
				+ ", focusFlag=" + focusFlag
				+ ", write_ArticleTime=" + write_ArticleTime + "]";
	}
	
	

}
