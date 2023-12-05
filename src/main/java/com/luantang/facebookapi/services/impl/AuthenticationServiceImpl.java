package com.luantang.facebookapi.services.impl;

import com.luantang.facebookapi.dto.AuthResponseDto;
import com.luantang.facebookapi.dto.LoginDto;
import com.luantang.facebookapi.dto.RegisterDto;
import com.luantang.facebookapi.dto.UserDto;
import com.luantang.facebookapi.exceptions.UserNotFoundException;
import com.luantang.facebookapi.models.Role;
import com.luantang.facebookapi.models.UserEntity;
import com.luantang.facebookapi.services.AuthenticationService;
import com.luantang.facebookapi.services.CustomUserDetailsService;
import com.luantang.facebookapi.services.JWTService;
import com.luantang.facebookapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CustomUserDetailsService customUserDetailsService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Autowired
    public AuthenticationServiceImpl(CustomUserDetailsService customUserDetailsService, UserService userService, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.customUserDetailsService = customUserDetailsService;
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
            UserEntity user = mapRegisterDtoToEntity(registerDto);
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

    private UserEntity mapRegisterDtoToEntity(RegisterDto registerDto) {
        UserEntity user = new UserEntity();
        user.setUserId(registerDto.getUserId());
        user.setUserName(registerDto.getUserName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setUserImage(registerDto.getUserImage());
        user.setRole(Role.USER);
        user.setActivityStatus(true);
        user.setJoiningDate(new Date());
        return user;
    }
}
