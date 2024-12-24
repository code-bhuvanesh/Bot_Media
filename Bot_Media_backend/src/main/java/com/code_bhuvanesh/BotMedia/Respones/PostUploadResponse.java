package com.code_bhuvanesh.BotMedia.Respones;

public class PostUploadResponse extends MainResponse {
    private long postId;

    public long getPostId() {
        return postId;
    }

    public PostUploadResponse setPostId(long postId) {
        this.postId = postId;
        return this;
    }

    @Override
    public PostUploadResponse setResponse(ResponseType response) {
        super.setResponse(response);
        return this;
    }
}
