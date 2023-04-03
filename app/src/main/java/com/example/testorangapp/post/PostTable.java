package com.example.testorangapp.post;

public class PostTable {

    private String postUser;
    private String category ;
    private String mainImageUrl;
    private String imageUrl;
    private String title;
    private String content;
    private int flag;
    //flag default n = 0;
    private String applyLink;
    private String createDate;
    private String expireDate;
    //flag default n = "0";


    public PostTable() {
        this.flag = 0;
        this.applyLink = "NullLink";
        this.expireDate = "NullDate";
    }

    public String getPostUser() {
        return postUser;
    }

    public void setPostUser(String postUser) {
        this.postUser = postUser;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getApplyLink() {
        return applyLink;
    }

    public void setApplyLink(String applyLink) {
        this.applyLink = applyLink;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
}
