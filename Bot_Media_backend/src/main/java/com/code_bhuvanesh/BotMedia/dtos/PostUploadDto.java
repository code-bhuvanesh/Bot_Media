package com.code_bhuvanesh.BotMedia.dtos;

import org.springframework.web.multipart.MultipartFile;

public class PostUploadDto {

    private String postContent;
    private MultipartFile postImage;


    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;

    }

    public MultipartFile getPostImage() {
        return postImage;
    }

    public void setPostImage(MultipartFile postImage) {
        this.postImage = postImage;
    }


}
