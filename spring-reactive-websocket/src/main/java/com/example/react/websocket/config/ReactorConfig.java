package com.example.react.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Configuration
public class ReactorConfig {
    @Bean
    public Scheduler scheduler() {
        int threadPoolSize = 10; // 원하는 스레드 풀 크기
        return Schedulers.newParallel("reactor-pool", threadPoolSize);
    }
}
