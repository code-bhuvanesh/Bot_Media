package com.code_bhuvanesh.BotMedia.Respones;

public class PostResponse {
    private long id;
    private String postContent;
    private String fileUrl;
    private String username;
    private long userid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String filename) {
        this.fileUrl = filename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }
}
