package com.example.react.mongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // ReactorContextWebFilter 에 적용할 시큐리티 필터
    public SecurityWebFilterChain securityFilterChainConfigurer(ServerHttpSecurity httpSecurity) {
        // 기존 mvc 에서 사용하던 HttpSecurity 에서 webflux 용으로 정의된 ServerHttpSecurity
        // 기존 spring security 사용 방식과 크게 다르지 않다.
        return httpSecurity
                .authorizeExchange()
                .anyExchange().permitAll().and()
                .httpBasic().and()
                .formLogin().and()
                .build();
    }
}
