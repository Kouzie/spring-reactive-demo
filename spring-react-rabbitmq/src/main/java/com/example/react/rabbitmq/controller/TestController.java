package com.example.react.rabbitmq.controller;


import com.example.react.rabbitmq.component.RabbitmqComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final RabbitmqComponent rabbitmqComponent;

    @GetMapping
    public Mono<Boolean> testSendMessage() {
        return rabbitmqComponent.convertAndSend("test");
    }

    @PostMapping
    public Mono<Boolean> testSendMessage2() {
        return rabbitmqComponent.convertAndSend("test");
    }

    @GetMapping("/listener")
    public Mono<String> testListener() {
        return rabbitmqComponent.processNewItemsViaSpringAmqp("test");
    }
}
