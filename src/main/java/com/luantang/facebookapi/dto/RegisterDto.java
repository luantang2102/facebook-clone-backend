package com.luantang.facebookapi.dto;

public class RegisterDto {
    private String userId;
    private String userName;
    private String email;
    private String password;
    private String userImage;
    private String coverImage;

    public RegisterDto(String userId, String userName, String email, String password, String userImage, String coverImage) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userImage = userImage;
        this.coverImage = coverImage;
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

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
