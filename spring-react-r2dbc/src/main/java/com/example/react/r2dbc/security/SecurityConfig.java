package com.example.react.r2dbc.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity // 다른곳에서도 security 관련 어노테이션 사용
public class SecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // ReactorContextWebFilter 에 적용할 시큐리티 필터
    public SecurityWebFilterChain securityFilterChainConfigurer(ServerHttpSecurity httpSecurity) {
        // 기존 mvc 에서 사용하던 HttpSecurity 에서 webflux 용으로 정의된 ServerHttpSecurity
        // 기존 spring security 사용 방식과 크게 다르지 않다.
        return httpSecurity
                .exceptionHandling()
                //.authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() -> // 미 로그인
                //        swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
                //.accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> // 미 로그인
                //        swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
                //.accessDeniedHandler((swe, e) -> Mono.error(new AccessDeniedException("")))
                .authenticationEntryPoint((swd, e) -> Mono.error(new AuthenticationCredentialsNotFoundException("")))
                .accessDeniedHandler((swe, e) -> Mono.error(new AccessDeniedException("")))
                .and()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .csrf().disable()
                .cors().disable()
                .httpBasic().disable() // Basic Authentication disable
                .formLogin().disable()
                .authorizeExchange()
                .pathMatchers("/member/join", "/member/login").permitAll()
                .pathMatchers("/member/**", "/rent/**").authenticated()
                .pathMatchers("/admin/**").hasAnyRole("ROLE_ADMIN")
                .anyExchange().permitAll()
                .and()
                .build();
    }

    private class CustomExceptionHandler implements ServerAuthenticationEntryPoint {
        @Override
        public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
            return null;
        }
    }

    /*private class CustomAccessDeniedHandler implements ServerAccessDeniedHandler {

        @Override
        public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
            throw denied;
        }
    }*/
}
