package com.example.rsocket.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.messaging.handler.invocation.reactive.AuthenticationPrincipalArgumentResolver;
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor;

@Configuration
public class SecurityConfig {

    @Bean
    MapReactiveUserDetailsService authentication() {
        return new MapReactiveUserDetailsService(User
                .withUsername("kouzie")
                .password("{noop}password")
                .roles("USER")
                .build());
    }

    @Bean
    PayloadSocketAcceptorInterceptor authorization(RSocketSecurity rSocketSecurity) {
        return rSocketSecurity
                .authorizePayload(authorizePayloadsSpec -> authorizePayloadsSpec
                        .anyExchange()
                        .authenticated()) // 모든 요청 인증 절차 필요
                .simpleAuthentication(Customizer.withDefaults()) //
                .build();
    }


    @Bean
    RSocketMessageHandler messageHandler(RSocketStrategies strategies) {
        // 메세지 컨트롤러에서 @AuthenticationPrincipal 를 사용하기 위한 설정
        RSocketMessageHandler rmh = new RSocketMessageHandler();
        rmh.setRSocketStrategies(strategies);
        rmh.getArgumentResolverConfigurer().addCustomResolver(new AuthenticationPrincipalArgumentResolver());
        return rmh;
    }
}
