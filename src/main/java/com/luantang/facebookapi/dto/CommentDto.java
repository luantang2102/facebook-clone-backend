package com.luantang.facebookapi.dto;

import java.util.Date;
import java.util.UUID;

public class CommentDto {
    private UUID commentId;
    private String userId;
    private String userName;
    private String imageURL;
    private UUID postId;
    private String comment;
    private Date timeSummit;

    public CommentDto() {
    }

    public CommentDto(UUID commentId, String userId, String userName, String imageURL, UUID postId, String comment, Date timeSummit) {
        this.commentId = commentId;
        this.userId = userId;
        this.userName = userName;
        this.imageURL = imageURL;
        this.postId = postId;
        this.comment = comment;
        this.timeSummit = timeSummit;
    }

    public UUID getCommentId() {
        return commentId;
    }

    public void setCommentId(UUID commentId) {
        this.commentId = commentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getTimeSummit() {
        return timeSummit;
    }

    public void setTimeSummit(Date timeSummit) {
        this.timeSummit = timeSummit;
    }
}
