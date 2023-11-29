package com.luantang.facebookapi.services.impl;

import com.luantang.facebookapi.models.UserEntity;
import com.luantang.facebookapi.repositories.UserRepository;
import com.luantang.facebookapi.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    UserRepository userRepository;

    private UserEntity userDetails;

    @Autowired
    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName /*email*/) throws UsernameNotFoundException {
        userDetails = userRepository.findByEmail(userName /*email*/).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userDetails;
    }

    @Override
    public UserEntity getUserDetails() {
        return userDetails;
    }
}
