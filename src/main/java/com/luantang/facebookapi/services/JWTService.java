package com.luantang.facebookapi.services;

import com.luantang.facebookapi.dto.UserDto;
import com.luantang.facebookapi.models.Role;
import com.luantang.facebookapi.models.UserEntity;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String generateToken(UserDto currentUser);
    Claims extractAllClaims(String token);
    String extractUserId(String token);
    boolean validateToken(String token);
}
