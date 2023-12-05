package com.luantang.facebookapi.services;

import com.luantang.facebookapi.dto.UserDto;
import com.luantang.facebookapi.dto.response.UserResponse;
import com.luantang.facebookapi.models.UserEntity;


public interface UserService {
    UserResponse getUsers(int pageNo, int pageSize);
    UserDto getCurrentUser();
    UserDto getUserByEmail(String email);
    UserDto getUserById(String userId);
    UserDto createUser(UserEntity user);
}
