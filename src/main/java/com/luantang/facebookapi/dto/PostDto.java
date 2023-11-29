package com.luantang.facebookapi.dto;

import java.util.Date;
import java.util.UUID;

public class PostDto {
    private UUID postId;
    private String userName;
    private String imageURL;
    private String description;
    private String postImgURL;
    private int likes;
    private Date dateTime;

    public PostDto() {
    }

    public PostDto(UUID postId, String userName, String imageURL, String description, String postImgURL, int likes, Date dateTime) {
        this.postId = postId;
        this.userName = userName;
        this.imageURL = imageURL;
        this.description = description;
        this.postImgURL = postImgURL;
        this.likes = likes;
        this.dateTime = dateTime;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostImgURL() {
        return postImgURL;
    }

    public void setPostImgURL(String postImgURL) {
        this.postImgURL = postImgURL;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
