package com.luantang.facebookapi.config.controllers;

import com.luantang.facebookapi.dto.AuthResponseDto;
import com.luantang.facebookapi.dto.LoginDto;
import com.luantang.facebookapi.dto.RegisterDto;
import com.luantang.facebookapi.dto.UserDto;
import com.luantang.facebookapi.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        UserDto user = authenticationService.register(registerDto);
        if(Objects.isNull(user)) {
            return new ResponseEntity<>("Email already exist", HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>("Successfully Registered", HttpStatus.CREATED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(authenticationService.login(loginDto), HttpStatus.OK);
    }
}

