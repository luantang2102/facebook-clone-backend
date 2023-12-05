package com.luantang.facebookapi.services;

import com.luantang.facebookapi.models.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailsService extends UserDetailsService {
    UserDetails loadUserById(String userId /*email*/) throws UsernameNotFoundException;
    UserEntity getUserDetails();
}
