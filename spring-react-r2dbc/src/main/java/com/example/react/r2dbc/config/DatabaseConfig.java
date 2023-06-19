package com.example.react.r2dbc.config;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.h2.H2ConnectionOption;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class DatabaseConfig {
    private Server webServer;

    @EventListener(ContextRefreshedEvent.class)
    public void start() throws java.sql.SQLException {
        log.info("starting h2 console at port 8078");
        this.webServer = Server.createWebServer("-webPort", "8078", "-tcpAllowOthers").start();
    }

    @EventListener(ContextClosedEvent.class)
    public void stop() {
        log.info("stopping h2 console at port 8078");
        this.webServer.stop();
    }

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
        resourceDatabasePopulator.addScript(new ClassPathResource("create.sql"));
        initializer.setConnectionFactory(connectionFactory());
        initializer.setDatabasePopulator(resourceDatabasePopulator);
        return initializer;
    }

}
