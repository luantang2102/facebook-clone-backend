package com.luantang.facebookapi.services;

import com.luantang.facebookapi.dto.UserDto;
import io.jsonwebtoken.Claims;

public interface JWTService {
    String generateToken(UserDto currentUser);
    Claims extractAllClaims(String token);
    String extractUserId(String token);
    boolean validateToken(String token);
}
