package com.code_bhuvanesh.BotMedia.Respones;



public class MainResponse {

    private String response;

    public String getResponse() {
        return response;
    }

    public MainResponse setResponse(ResponseType response) {
        if(response == ResponseType.SUCCESS){
            this.response = "success";
        }
        else{
            this.response = "error";
        }
        return this;
    }


}
