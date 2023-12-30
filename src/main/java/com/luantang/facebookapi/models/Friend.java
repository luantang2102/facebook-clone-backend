package com.luantang.facebookapi.models;

import com.luantang.facebookapi.models.enums.FriendStatus;

import java.util.Date;

public class Friend {
    private String userId;
    private FriendStatus friendStatus;
    private Date connectDate;

    public Friend() {
    }

    public Friend(String userId, FriendStatus friendStatus, Date connectDate) {
        this.userId = userId;
        this.friendStatus = friendStatus;
        this.connectDate = connectDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public FriendStatus getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(FriendStatus friendStatus) {
        this.friendStatus = friendStatus;
    }

    public Date getConnectDate() {
        return connectDate;
    }

    public void setConnectDate(Date connectDate) {
        this.connectDate = connectDate;
    }
}
