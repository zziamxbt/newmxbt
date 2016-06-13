package com.example.acer.zzia_mxbt.bean;

import java.util.List;

public class ArticleBean {
    private List<Integer> user_id;//文章章节的作者
    private List<String> author_chapter_name;//文章章节的作者
    private List<String> chapter_content;//文章章节的内容
    private List<String> chapter_number;//文章章节的章节号
    private List<String> create_chapter_time;//创建文章章节时间
    private List<String> author_chapter_head;//文章章节作者的头像
    private int chapter_id; //章节id
    private String author_name;  //文章的作者
    private String article_chapter;  //文章的章节
    private String article_cover;    //文章封面
    private String article_background; //文章背景
    private String article_title;   //文章题目
    private String article_type;    //文章类型
    private String author_headportrait;   //写文章作者的头像
    private String author_sex;     //作者性别
    private int focus_number;      //关注数
    private int reader_number;     //读者
    private boolean recommandFalg;  //判断是否推荐
    private boolean collectFalg;  //判断是否收藏

    public ArticleBean() {
    }


    public ArticleBean(String author_name, String article_chapter,
                       String article_cover, String article_background,
                       String article_title, String article_type,
                       String author_headportrait,
                       String author_sex, int focus_number, int reader_number,
                       List<String> author_chapter_name, List<String> chapter_content,
                       List<String> chapter_number, List<String> create_chapter_time
            , List<String> author_chapter_head, int chapter_id,
                       boolean recommandFalg, boolean collectFalg,List<Integer> user_id) {
        super();
        this.author_name = author_name;
        this.article_chapter = article_chapter;
        this.article_cover = article_cover;
        this.article_background = article_background;
        this.article_title = article_title;
        this.article_type = article_type;
        this.author_headportrait = author_headportrait;
        this.author_sex = author_sex;
        this.focus_number = focus_number;
        this.reader_number = reader_number;
        this.author_chapter_name = author_chapter_name;
        this.chapter_content = chapter_content;
        this.chapter_number = chapter_number;
        this.create_chapter_time = create_chapter_time;
        this.author_chapter_head = author_chapter_head;
        this.chapter_id = chapter_id;
        this.recommandFalg = recommandFalg;
        this.collectFalg = collectFalg;
        this.user_id=user_id;
    }


    public List<Integer> getUser_id() {
        return user_id;
    }

    public void setUser_id(List<Integer> user_id) {
        this.user_id = user_id;
    }

    public boolean isRecommandFalg() {
        return recommandFalg;
    }

    public void setRecommandFalg(boolean recommandFalg) {
        this.recommandFalg = recommandFalg;
    }

    public boolean isCollectFalg() {
        return collectFalg;
    }

    public void setCollectFalg(boolean collectFalg) {
        this.collectFalg = collectFalg;
    }

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }

    public List<String> getAuthor_chapter_head() {
        return author_chapter_head;
    }


    public void setAuthor_chapter_head(List<String> author_chapter_head) {
        this.author_chapter_head = author_chapter_head;
    }


    public List<String> getCreate_chapter_time() {
        return create_chapter_time;
    }


    public void setCreate_chapter_time(List<String> create_chapter_time) {
        this.create_chapter_time = create_chapter_time;
    }


    public List<String> getChapter_content() {
        return chapter_content;
    }


    public void setChapter_content(List<String> chapter_content) {
        this.chapter_content = chapter_content;
    }


    public List<String> getChapter_number() {
        return chapter_number;
    }


    public void setChapter_number(List<String> chapter_number) {
        this.chapter_number = chapter_number;
    }


    public String getArticle_title() {
        return article_title;
    }


    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }


    public List<String> getAuthor_chapter_name() {
        return author_chapter_name;
    }


    public void setAuthor_chapter_name(List<String> author_chapter_name) {
        this.author_chapter_name = author_chapter_name;
    }


    public String getArticle_background() {
        return article_background;
    }


    public void setArticle_background(String article_background) {
        this.article_background = article_background;
    }


    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getArticle_chapter() {
        return article_chapter;
    }

    public void setArticle_chapter(String article_chapter) {
        this.article_chapter = article_chapter;
    }

    public String getArticle_cover() {
        return article_cover;
    }

    public void setArticle_cover(String article_cover) {
        this.article_cover = article_cover;
    }

    public String getArticle_type() {
        return article_type;
    }

    public void setArticle_type(String article_type) {
        this.article_type = article_type;
    }

    public String getAuthor_headportrait() {
        return author_headportrait;
    }

    public void setAuthor_headportrait(String author_headportrait) {
        this.author_headportrait = author_headportrait;
    }

    public String getAuthor_sex() {
        return author_sex;
    }

    public void setAuthor_sex(String author_sex) {
        this.author_sex = author_sex;
    }

    public int getFocus_number() {
        return focus_number;
    }

    public void setFocus_number(int focus_number) {
        this.focus_number = focus_number;
    }

    public int getReader_number() {
        return reader_number;
    }

    public void setReader_number(int reader_number) {
        this.reader_number = reader_number;
    }


    @Override
    public String toString() {
        return "ArticleBean{" +
                "author_chapter_name=" + author_chapter_name +
                ", chapter_content=" + chapter_content +
                ", chapter_number=" + chapter_number +
                ", create_chapter_time=" + create_chapter_time +
                ", author_chapter_head=" + author_chapter_head +
                ", author_name='" + author_name + '\'' +
                ", article_chapter='" + article_chapter + '\'' +
                ", article_cover='" + article_cover + '\'' +
                ", article_background='" + article_background + '\'' +
                ", article_title='" + article_title + '\'' +
                ", article_type='" + article_type + '\'' +
                ", author_headportrait='" + author_headportrait + '\'' +
                ", author_sex='" + author_sex + '\'' +
                ", focus_number=" + focus_number +
                ", reader_number=" + reader_number +
                ", user_id=" + user_id +
                '}';
    }

}
