package com.luantang.facebookapi.models;

import com.luantang.facebookapi.models.enums.ConnectStatus;
import com.luantang.facebookapi.models.enums.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Document("User")
public class UserEntity implements UserDetails {

    @Id
    private String userId;
    private String userName;
    private String userImage;
    private String coverImage;
    private String email;
    private String password;
    private Role role;
    private boolean activityStatus;
    private Date joiningDate;
    private int totalFriends;
    private List<Friend> friendIdList;
    private ConnectStatus connectStatus;

    public UserEntity() {
    }

    public UserEntity(String userId, String userName, String userImage, String coverImage, String email, String password, Role role, boolean activityStatus, Date joiningDate, int totalFriends, List<Friend> friendIdList, ConnectStatus connectStatus) {
        this.userId = userId;
        this.userName = userName;
        this.userImage = userImage;
        this.coverImage = coverImage;
        this.email = email;
        this.password = password;
        this.role = role;
        this.activityStatus = activityStatus;
        this.joiningDate = joiningDate;
        this.totalFriends = totalFriends;
        this.friendIdList = friendIdList;
        this.connectStatus = connectStatus;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getPassword() {
        return password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
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

    public ConnectStatus getConnectStatus() {
        return connectStatus;
    }

    public void setConnectStatus(ConnectStatus connectStatus) {
        this.connectStatus = connectStatus;
    }
}
