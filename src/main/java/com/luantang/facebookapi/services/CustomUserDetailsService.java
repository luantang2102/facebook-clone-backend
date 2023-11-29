package com.luantang.facebookapi.services;

import com.luantang.facebookapi.models.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {
    UserEntity getUserDetails();
}
