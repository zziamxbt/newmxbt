package com.example.acer.zzia_mxbt.bean;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

/**
 * Created by 刘俊杰 on 2016/6/8.
 */
public class downlineBean implements Serializable {
    int Aid;
    ByteArrayInputStream Uhead;
    ByteArrayInputStream Acoverimg;
    String Unickname;
    String Akind;
    String Atitle;
    public downlineBean(){}

    public downlineBean(int aid, ByteArrayInputStream uhead, ByteArrayInputStream acoverimg, String unickname, String akind, String atitle) {
        Aid = aid;
        Uhead = uhead;
        Acoverimg = acoverimg;
        Unickname = unickname;
        Akind = akind;
        Atitle = atitle;
    }

    public int getAid() {
        return Aid;
    }

    public void setAid(int aid) {
        Aid = aid;
    }

    public ByteArrayInputStream getUhead() {
        return Uhead;
    }

    public void setUhead(ByteArrayInputStream uhead) {
        Uhead = uhead;
    }

    public ByteArrayInputStream getAcoverimg() {
        return Acoverimg;
    }

    public void setAcoverimg(ByteArrayInputStream acoverimg) {
        Acoverimg = acoverimg;
    }

    public String getUnickname() {
        return Unickname;
    }

    public void setUnickname(String unickname) {
        Unickname = unickname;
    }

    public String getAkind() {
        return Akind;
    }

    public void setAkind(String akind) {
        Akind = akind;
    }

    public String getAtitle() {
        return Atitle;
    }

    public void setAtitle(String atitle) {
        Atitle = atitle;
    }
}
