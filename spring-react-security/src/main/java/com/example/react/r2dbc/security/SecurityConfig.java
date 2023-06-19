package com.example.react.r2dbc.security;

import com.example.react.r2dbc.model.Member;
import com.example.react.r2dbc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
public class SecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner demo(MemberRepository repository, PasswordEncoder encoder) {
        return (args) -> {
            // save a few customers
            repository.saveAll(Arrays.asList(
                    Member.builder().name("Kim").password(encoder.encode("Kim")).userName("Kim@test.com").role("ROLE_ADMIN").build(),
                    Member.builder().name("Chloe").password(encoder.encode("Chloe")).userName("Chloe@test.com").role("ROLE_MEMBER").build(),
                    Member.builder().name("David").password(encoder.encode("David")).userName("David@test.com").role("ROLE_MEMBER").build(),
                    Member.builder().name("Michelle").password(encoder.encode("Michelle")).userName("Michelle@test.com").role("ROLE_MEMBER").build()
            )).blockLast(Duration.ofSeconds(10));

            // fetch all customers
            log.info("Member found with findAll():");
            log.info("-------------------------------");
            repository.findAll().doOnNext(member -> {
                log.info(member.toString());
            }).blockLast(Duration.ofSeconds(10));

            // fetch an individual customer by ID
            repository.findById(1L).doOnNext(member -> {
                log.info("Member found with findById(1L):");
                log.info("--------------------------------");
                log.info(member.toString());
                log.info("");
            }).block(Duration.ofSeconds(10));

            // fetch customers by last name
            log.info("Member found with findByLastName('Chloe'):");
            log.info("--------------------------------------------");
            repository.findByName("Chloe").doOnNext(member -> {
                log.info(member.toString());
            }).block(Duration.ofSeconds(10));
        };
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
                .pathMatchers("/auth/**").permitAll()
                .pathMatchers("/member/**").authenticated()
                .pathMatchers("/admin/**").hasRole("ROLE_ADMIN")
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
