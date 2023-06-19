package com.example.react.r2dbc.config;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.h2.H2ConnectionOption;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DbConnectConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        log.info("H2 connectionFactory invoked");
        Map<String, String> options = new HashMap<>();
        options.put("TimeZone", "Asia/Seoul");
        return new H2ConnectionFactory(H2ConnectionConfiguration.builder()
                .inMemory("test")
                .property(H2ConnectionOption.DB_CLOSE_DELAY, "-1")
                .property("DATABASE_TO_UPPER", "false")
                .username("sa")
                .build());
    }

    @Bean
    public ConnectionFactoryInitializer h2DbInitializer() {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("schema.sql"));
        initializer.setConnectionFactory(connectionFactory());
        initializer.setDatabasePopulator(resourceDatabasePopulator);
        return initializer;
    }
}
