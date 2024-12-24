package com.code_bhuvanesh.BotMedia.Respones;

import java.util.List;

public class PostGetResponse {
    private List<PostResponse> postResponses;

    public PostGetResponse(List<PostResponse> postResponses) {
        this.postResponses = postResponses;
    }

    public List<PostResponse> getPostResponses() {
        return postResponses;
    }

    public void setPostResponses(List<PostResponse> postResponses) {
        this.postResponses = postResponses;
    }
}
