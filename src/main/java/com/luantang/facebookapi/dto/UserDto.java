package com.luantang.facebookapi.dto;

import com.luantang.facebookapi.models.Role;

import java.util.Date;
import java.util.UUID;

public class UserDto {
    private String userId;
    private String userName;
    private String email;
    private String password;
    private String userImage;
    private Role role;
    private boolean activityStatus;
    private Date joiningDate;

    public UserDto() {
    }

    public UserDto(String userId, String userName, String email, String password, String userImage, Role role, boolean activityStatus, Date joiningDate) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userImage = userImage;
        this.role = role;
        this.activityStatus = activityStatus;
        this.joiningDate = joiningDate;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(boolean activityStatus) {
        this.activityStatus = activityStatus;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }
}
