package com.example.godhoyeong.hyexrecyclerview.data;

public class DataPostItem {

    int postIdx;

    String postImgUrl;
    String postText;
    String userName;

    int post_likes_count;
    boolean isUserLiked;

    public DataPostItem(int postIdx, String postImgUrl, String postText, String userName, int post_likes_count, boolean isUserLiked) {
        this.postIdx = postIdx;
        this.postImgUrl = postImgUrl;
        this.postText = postText;
        this.userName = userName;
        this.post_likes_count = post_likes_count;
        this.isUserLiked = isUserLiked;
    }

    public int getPostIdx() {
        return postIdx;
    }

    public String getPostImgUrl() {
        return postImgUrl;
    }

    public String getPostText() {
        return postText;
    }

    public String getUserName() {
        return userName;
    }

    public int getPost_likes_count() {
        return post_likes_count;
    }

    public boolean isUserLiked() {
        return isUserLiked;
    }
}
