package com.luantang.facebookapi.services.impl;

import com.luantang.facebookapi.constants.SecurityConstants;
import com.luantang.facebookapi.dto.UserDto;
import com.luantang.facebookapi.exceptions.JwtInvalidException;
import com.luantang.facebookapi.services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {

    private Key getSignInKey() {
        byte[] key = Decoders.BASE64.decode(SecurityConstants.JWT_SECRET);
        return Keys.hmacShaKeyFor(key);
    }

    @Override
    public String generateToken(UserDto currentUser) {
        String userId = currentUser.getUserId();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);
        Map<String , Object> claims = new HashMap<>();
        claims.put("role", currentUser.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
            //token
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String token) {
        try {
            return extractClaim(token, Claims::getExpiration).after(new Date());
        }
        catch (Exception ex) {
            throw new JwtInvalidException("JWT was expired");
        }
    }

}
