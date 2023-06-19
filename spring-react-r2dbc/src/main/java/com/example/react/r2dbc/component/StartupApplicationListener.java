package com.example.react.r2dbc.component;


import com.example.react.r2dbc.service.StockInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final StockInfoService service;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        service.initData();
    }
}