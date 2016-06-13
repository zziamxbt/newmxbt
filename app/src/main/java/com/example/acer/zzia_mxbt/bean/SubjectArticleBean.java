package com.example.acer.zzia_mxbt.bean;

/**
 * Created by qiyu on 2016/6/1.
 */
public class SubjectArticleBean {
    // 专题对应的文章Bean
    private static final long serialVersionUID = 1L;
    int articleId;// 文章id
    String headImg;// 用户头像
    String nickName;// 用户昵称
    String time;// 文章创建时间
    String kind;// 文章类型
    String background;// 文章背景
    String title;// 文章标题
    String content;// 文章第一段内容
    int collectNum;// 收藏数量
    int recomendNum;// 推荐数量
    int commentNum;// 评论数量

    public SubjectArticleBean() {

    }

    public SubjectArticleBean(int articleId, String headImg, String nickName,
                              String time, String kind, String background, String title,
                              String content, int collectNum, int recomendNum, int commentNum) {
        super();
        this.articleId = articleId;
        this.headImg = headImg;
        this.nickName = nickName;
        this.time = time;
        this.kind = kind;
        this.background = background;
        this.title = title;
        this.content = content;
        this.collectNum = collectNum;
        this.recomendNum = recomendNum;
        this.commentNum = commentNum;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(int collectNum) {
        this.collectNum = collectNum;
    }

    public int getRecomendNum() {
        return recomendNum;
    }

    public void setRecomendNum(int recomendNum) {
        this.recomendNum = recomendNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    @Override
    public String toString() {
        return "SubjectArticleBean [articleId=" + articleId + ", headImg="
                + headImg + ", nickName=" + nickName + ", time=" + time
                + ", kind=" + kind + ", background=" + background + ", title="
                + title + ", content=" + content + ", collectNum=" + collectNum
                + ", recomendNum=" + recomendNum + ", commentNum=" + commentNum
                + "]";
    }
}
