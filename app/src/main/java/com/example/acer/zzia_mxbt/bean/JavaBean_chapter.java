package com.example.acer.zzia_mxbt.bean;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by Gemptc on 2016/5/16.
 */
public class JavaBean_chapter implements Serializable {
    boolean flag=true;
    private String ChapterNumber;
    private String ArticleEasyIntroduction;

    public JavaBean_chapter(){};

    public JavaBean_chapter(String chapterNumber, String articleEasyIntroduction) {
        ChapterNumber = chapterNumber;
        ArticleEasyIntroduction = articleEasyIntroduction;
    }

    public String getChapterNumber() {
        return ChapterNumber;
    }

    public void setChapterNumber(String chapterNumber) {
        ChapterNumber = chapterNumber;
    }

    public String getArticleEasyIntroduction() {
        return ArticleEasyIntroduction;
    }

    public void setArticleEasyIntroduction(String articleEasyIntroduction) {
        ArticleEasyIntroduction = articleEasyIntroduction;
    }
}
