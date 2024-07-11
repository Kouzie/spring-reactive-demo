package com.example.react.r2dbc.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final MyAuthenticationManager myAuthenticationManager;
    private final JwtTokenUtil jwtUtil;

    @Override
    public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange swe) {
        ServerHttpRequest request = swe.getRequest();
        String authToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authToken == null || !jwtUtil.validateToken(authToken))
            return Mono.empty();

        Authentication auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
        Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
        String role = claims.get("role", String.class);
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
        return Mono.just(new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities))
                .map(authentication -> new SecurityContextImpl(authentication));
//            return myAuthenticationManager
//                    .authenticate(auth)
//                    .map(authentication -> new SecurityContextImpl(authentication));
    }
}