package com.luantang.facebookapi.dto;

import com.luantang.facebookapi.models.Friend;
import com.luantang.facebookapi.models.enums.Role;

import java.util.Date;
import java.util.List;

public class UserDto {
    private String userId;
    private String userName;
    private String userImage;
    private String userCoverImage;
    private String email;
    private Role role;

    private boolean activityStatus;
    private Date joiningDate;

    private int totalFriends;
    private List<Friend> friendIdList;
    public UserDto() {
    }

    public UserDto(String userId, String userName, String userImage, String userCoverImage, String email, Role role, boolean activityStatus, Date joiningDate, int totalFriends, List<Friend> friendIdList) {
        this.userId = userId;
        this.userName = userName;
        this.userImage = userImage;
        this.userCoverImage = userCoverImage;
        this.email = email;
        this.role = role;
        this.activityStatus = activityStatus;
        this.joiningDate = joiningDate;
        this.totalFriends = totalFriends;
        this.friendIdList = friendIdList;
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

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserCoverImage() {

        return userCoverImage;
    }

    public void setUserCoverImage(String userCoverImage) {
        this.userCoverImage = userCoverImage;
    }

    public boolean isActive() {
        return activityStatus;
    }

    public void setActivityStatus(boolean activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public int getTotalFriends() {
        return totalFriends;
    }

    public void setTotalFriends(int totalFriends) {
        this.totalFriends = totalFriends;
    }

    public List<Friend> getFriendIdList() {
        return friendIdList;
    }

    public void setFriendIdList(List<Friend> friendIdList) {
        this.friendIdList = friendIdList;
    }
}
