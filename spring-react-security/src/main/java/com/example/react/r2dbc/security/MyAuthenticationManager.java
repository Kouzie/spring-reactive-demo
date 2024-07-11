package com.example.react.r2dbc.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MyAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtTokenUtil jwtUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        if (!jwtUtil.validateToken(authToken)) {
            return Mono.empty();
        }
        Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
        String role = claims.get("role", String.class);
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
        return Mono.just(new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities));
    }
}
