package com.example.react.r2dbc.security;

import com.example.react.r2dbc.model.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt token 을 다루기위한 유틸, jwt 생성, 사용자 정보 가져오기, 유효시간 확인
 */
@Component
public class JwtTokenUtil implements Serializable {
    private static final String JWT_SECRET = "2c769da618e0febe7cba60f984274df9f9fe4bca1b46474c4c442bac00e57ebf2873";
    private static final Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    private static final int JWT_TOKEN_VALIDITY = 60 * 60; // 60 초 * 60

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", member.getId());
        claims.put("role", member.getRole());
        return doGenerateToken(claims, member.getUserName());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + JWT_TOKEN_VALIDITY * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}