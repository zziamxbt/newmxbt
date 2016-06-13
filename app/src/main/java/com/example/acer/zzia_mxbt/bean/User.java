package com.example.acer.zzia_mxbt.bean;

import java.io.Serializable;

/**
 * Created by acer on 2016/5/28.
 */
public class User implements Serializable{


    private static final long serialVersionUID = 1L;
    private int Uid;//主键
    private String Uhead;//头像
    private String Uname;//帐号
    private String Unickname;//昵称
    private String Utoken;//聊天

    private String Ubk;//背景图片
    private String Usex;//性别
    private String Ucountry;//国家
    private String Usign;//个性签名
    private String Upassword;//密码


    public User(){

    }

    public User(int uid, String uhead, String ubk, String uname, String unickname, String usex, String ucountry, String usign, String upassword, String utoken) {
        Uid = uid;
        Uhead = uhead;
        Ubk = ubk;
        Uname = uname;
        Unickname = unickname;
        Usex = usex;
        Ucountry = ucountry;
        Usign = usign;
        Upassword = upassword;
        Utoken = utoken;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    public String getUhead() {
        return Uhead;
    }

    public void setUhead(String uhead) {
        Uhead = uhead;
    }

    public String getUbk() {
        return Ubk;
    }

    public void setUbk(String ubk) {
        Ubk = ubk;
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public String getUnickname() {
        return Unickname;
    }

    public void setUnickname(String unickname) {
        Unickname = unickname;
    }

    public String getUsex() {
        return Usex;
    }

    public void setUsex(String usex) {
        Usex = usex;
    }

    public String getUcountry() {
        return Ucountry;
    }

    public void setUcountry(String ucountry) {
        Ucountry = ucountry;
    }

    public String getUsign() {
        return Usign;
    }

    public void setUsign(String usign) {
        Usign = usign;
    }

    public String getUpassword() {
        return Upassword;
    }

    public void setUpassword(String upassword) {
        Upassword = upassword;
    }

    public String getUtoken() {
        return Utoken;
    }

    public void setUtoken(String utoken) {
        Utoken = utoken;
    }

    @Override
    public String toString() {
        return "User{" +
                "Uid=" + Uid +
                ", Uhead='" + Uhead + '\'' +
                ", Ubk='" + Ubk + '\'' +
                ", Uname='" + Uname + '\'' +
                ", Unickname='" + Unickname + '\'' +
                ", Usex='" + Usex + '\'' +
                ", Ucountry='" + Ucountry + '\'' +
                ", Usign='" + Usign + '\'' +
                ", Upassword='" + Upassword + '\'' +
                ", Utoken='" + Utoken + '\'' +
                '}';
    }
}
