package com.luantang.facebookapi.services;

import com.luantang.facebookapi.dto.UserDto;
import com.luantang.facebookapi.dto.response.UserResponse;
import com.luantang.facebookapi.models.UserEntity;

import java.util.List;


public interface UserService {
    UserResponse getUsers(int pageNo, int pageSize);
    UserDto getCurrentUser();
    UserDto getUserByEmail(String email);
    UserDto getUserById(String userId);
    UserDto createUser(UserEntity user);

    UserDto updateUser(UserDto userDto);

    UserDto addFriendToCurrentUser(String saveUserId);

    UserDto removeFriendToCurrentUser(String removeUserId);

    boolean isFriend(String targetUserId);

    List<UserDto> getFriendListFromCurrentUser();
}
