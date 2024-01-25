package com.luantang.facebookapi.services.impl;

import com.luantang.facebookapi.dto.AuthResponseDto;
import com.luantang.facebookapi.dto.LoginDto;
import com.luantang.facebookapi.dto.RegisterDto;
import com.luantang.facebookapi.dto.UserDto;
import com.luantang.facebookapi.exceptions.UserNotFoundException;
import com.luantang.facebookapi.models.enums.ConnectStatus;
import com.luantang.facebookapi.models.enums.Role;
import com.luantang.facebookapi.models.UserEntity;
import com.luantang.facebookapi.services.AuthenticationService;
import com.luantang.facebookapi.services.JWTService;
import com.luantang.facebookapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Autowired
    public AuthenticationServiceImpl(UserService userService, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public UserDto register(RegisterDto registerDto) {
        try {
            userService.getUserByEmail(registerDto.getEmail());
            return null;
        }
        catch (UserNotFoundException ex) {
            UserEntity user = mapRegisterDtoToNewEntity(registerDto);
            return userService.createUser(user);
        }
    }

    @Override
    public AuthResponseDto login(LoginDto loginDto) {
        UserDto currentUser = userService.getUserByEmail(loginDto.getEmail());
        if(currentUser.isActive()) {
            String token = jwtService.generateToken(currentUser);
            return new AuthResponseDto(token, currentUser);
        }
        else {
            return new AuthResponseDto("Wait for admin approval", currentUser);
        }
    }

    private UserEntity mapRegisterDtoToNewEntity(RegisterDto registerDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(registerDto.getUserId());
        userEntity.setUserName(registerDto.getUserName());
        userEntity.setEmail(registerDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userEntity.setUserImage(registerDto.getUserImage());
        userEntity.setCoverImage(registerDto.getCoverImage());
        userEntity.setRole(Role.USER);
        userEntity.setActivityStatus(true);
        userEntity.setJoiningDate(new Date());
        userEntity.setTotalFriends(0);
        userEntity.setFriendIdList(new ArrayList<>());
        userEntity.setConnectStatus(ConnectStatus.ONLINE);

        return userEntity;
    }
}
