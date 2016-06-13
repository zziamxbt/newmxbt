package com.example.acer.zzia_mxbt.bean;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;

/**
 * Created by Gemptc on 2016/5/14.
 */
public class JavaBean_article implements Serializable {
    private boolean flagArticle=true;
    private String mAuthor_portraits;
    private String ChapterTitle;
    private String ChapterAuthorName;
    private String ArticleContent;
    private String ArticleTime;
    private int AuthorId;
    private int UserId;

    public JavaBean_article(){};

    public JavaBean_article(String chapterTitle, String articleTime, String chapterAuthorName, String articleContent
    ,String mAuthor_portraits,int AuthorId,int UserId) {
        ChapterTitle = chapterTitle;
        ArticleTime = articleTime;
        ChapterAuthorName = chapterAuthorName;
        ArticleContent = articleContent;
        UserId=UserId;
        AuthorId=AuthorId;
        mAuthor_portraits=mAuthor_portraits;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getAuthorId() {
        return AuthorId;
    }

    public void setAuthorId(int authorId) {
        AuthorId = authorId;
    }

    public String getmAuthor_portraits() {
        return mAuthor_portraits;
    }

    public void setmAuthor_portraits(String mAuthor_portraits) {
        this.mAuthor_portraits = mAuthor_portraits;
    }

    public String getChapterTitle() {
        return ChapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        ChapterTitle = chapterTitle;
    }

    public String getChapterAuthorName() {
        return ChapterAuthorName;
    }

    public void setChapterAuthorName(String chapterAuthorName) {
        ChapterAuthorName = chapterAuthorName;
    }

    public String getArticleContent() {
        return ArticleContent;
    }

    public void setArticleContent(String articleContent) {
        ArticleContent = articleContent;
    }

    public String getArticleTime() {
        return ArticleTime;
    }

    public void setArticleTime(String articleTime) {
        ArticleTime = articleTime;
    }
}
