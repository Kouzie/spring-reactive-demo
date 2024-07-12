package com.example.react.r2dbc.exception;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalErrorConfigure {
    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }
}
