package com.luantang.facebookapi.dto;

import java.util.Date;
import java.util.UUID;

public class StatusDto {
    private UUID statusId;
    private String userName;
    private String imageURL;
    private String statusImageURL;
    private Date uploadTime;

    public StatusDto() {
    }

    public StatusDto(UUID statusId, String userName, String imageURL, String statusImageURL, Date uploadTime) {
        this.statusId = statusId;
        this.userName = userName;
        this.imageURL = imageURL;
        this.statusImageURL = statusImageURL;
        this.uploadTime = uploadTime;
    }

    public UUID getStatusId() {
        return statusId;
    }

    public void setStatusId(UUID statusId) {
        this.statusId = statusId;
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

    public String getStatusImageURL() {
        return statusImageURL;
    }

    public void setStatusImageURL(String statusImageURL) {
        this.statusImageURL = statusImageURL;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
}
