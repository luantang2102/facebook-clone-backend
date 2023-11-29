package com.luantang.facebookapi.services;

import com.luantang.facebookapi.dto.AuthResponseDto;
import com.luantang.facebookapi.dto.LoginDto;
import com.luantang.facebookapi.dto.RegisterDto;
import com.luantang.facebookapi.dto.UserDto;

public interface AuthenticationService {
    public UserDto register(RegisterDto registerDto);
    public AuthResponseDto login(LoginDto loginDto);
}
