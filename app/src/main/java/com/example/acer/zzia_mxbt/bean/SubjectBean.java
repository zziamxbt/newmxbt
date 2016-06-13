package com.example.acer.zzia_mxbt.bean;

import java.io.Serializable;

/**
 * Created by qiyu on 2016/5/30.
 */
public class SubjectBean implements Serializable {
    /**
     * 对应subject表和image表
     */
    private static final long serialVersionUID = 1L;
    private int Sid;//专题id
    private int SIid;// 专题图片id
    private String path;//图片id对应的地址
    private String Scontent;//专题内容
    private String Stext;//专题简介地址

    public SubjectBean(){

    }


    public SubjectBean(int sid, int sIid, String path, String scontent,
                       String stext) {
        super();
        Sid = sid;
        SIid = sIid;
        this.path = path;
        Scontent = scontent;
        Stext = stext;
    }



    public int getSIid() {
        return SIid;
    }

    public void setSIid(int sIid) {
        SIid = sIid;
    }



    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }



    public String getScontent() {
        return Scontent;
    }


    public void setScontent(String scontent) {
        Scontent = scontent;
    }



    public String getStext() {
        return Stext;
    }


    public void setStext(String stext) {
        Stext = stext;
    }



    public int getSid() {
        return Sid;
    }


    public void setSid(int sid) {
        Sid = sid;
    }


    @Override
    public String toString() {
        return "SubjectBean [Sid=" + Sid + ", SIid=" + SIid + ", path=" + path
                + ", Scontent=" + Scontent + ", Stext=" + Stext + "]";
    }




}
