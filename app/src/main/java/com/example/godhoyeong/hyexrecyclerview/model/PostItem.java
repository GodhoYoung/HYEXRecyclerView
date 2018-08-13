package com.example.godhoyeong.hyexrecyclerview.model;

public class PostItem {

    int postLikeCount;
    boolean isUserLike;

    String userName;
    String postText;
    String postImgUrl;

    public PostItem(int postLikeCount, boolean isUserLike, String userName, String postText, String postImgUrl) {
        this.postLikeCount = postLikeCount;
        this.isUserLike = isUserLike;
        this.userName = userName;
        this.postText = postText;
        this.postImgUrl = postImgUrl;
    }

    public int getPostLikeCount() {
        return postLikeCount;
    }

    public boolean isUserLike() {
        return isUserLike;
    }

    public String getUserName() {
        return userName;
    }

    public String getPostText() {
        return postText;
    }

    public String getPostImgUrl() {
        return postImgUrl;
    }
}
