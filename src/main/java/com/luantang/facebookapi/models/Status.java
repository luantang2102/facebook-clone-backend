package com.luantang.facebookapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document("Status")
public class Status {
    @Id
    private UUID statusId;
    private String userId;
    private String statusImageURL;
    private Date uploadTime;

    public Status() {
    }

    public Status(UUID statusId, String userId, String statusImageURL, Date uploadTime) {
        this.statusId = statusId;
        this.userId = userId;
        this.statusImageURL = statusImageURL;
        this.uploadTime = uploadTime;
    }

    public UUID getStatusId() {
        return statusId;
    }

    public void setStatusId(UUID statusId) {
        this.statusId = statusId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
