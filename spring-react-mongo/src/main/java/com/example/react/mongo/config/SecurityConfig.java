package com.example.react.mongo.config;

import com.example.react.mongo.model.Member;
import com.example.react.mongo.repository.MemberRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.time.Duration;
import java.util.Arrays;

@Slf4j
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(MemberRepo repository) {
        return username -> repository.findByUserName(username)
                .map(member -> User.builder()
                        .username(member.getUserName())
                        .password(member.getPassword())
                        .authorities(member.getRole())
                        .build());
    }

    @Bean // ReactorContextWebFilter 에 적용할 시큐리티 필터
    public SecurityWebFilterChain securityFilterChainConfigurer(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .authorizeExchange().and()
                .httpBasic().and()
                .formLogin().and()
                .csrf().disable()
                .build();
    }


    @Bean
    public CommandLineRunner demo(MemberRepo repository, PasswordEncoder encoder) {
        return (args) -> {
            log.info("save start!");
            // save a few customers
            repository.saveAll(Arrays.asList(
                    Member.builder().name("Kim").password(encoder.encode("Kim")).userName("Kim@test.com").role("ROLE_ADMIN").build(),
                    Member.builder().name("Chloe").password(encoder.encode("Chloe")).userName("Chloe@test.com").role("ROLE_MEMBER").build(),
                    Member.builder().name("David").password(encoder.encode("David")).userName("David@test.com").role("ROLE_MEMBER").build(),
                    Member.builder().name("Michelle").password(encoder.encode("Michelle")).userName("Michelle@test.com").role("ROLE_MEMBER").build()
            )).blockLast(Duration.ofSeconds(10));
        };
    }
}
