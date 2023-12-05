package com.luantang.facebookapi.dto;

public class AuthResponseDto {
    private String accessToken;
    private UserDto currentUser;
    private String tokenType = "Bearer ";

    public AuthResponseDto(String accessToken, UserDto currentUser) {
        this.accessToken = accessToken;
        this.currentUser = currentUser;
    }

    public AuthResponseDto(String accessToken, UserDto currentUser, String tokenType) {
        this.accessToken = accessToken;
        this.currentUser = currentUser;
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public UserDto getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserDto currentUser) {
        this.currentUser = currentUser;
    }
}
