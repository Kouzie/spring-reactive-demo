package com.example.react.redis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/string")
@RequiredArgsConstructor
public class StringController {
    private final ReactiveRedisTemplate<String, String> redisTemplate;

    @GetMapping("/all")
    public Flux<String> getAll() {
        return redisTemplate.keys("*")
                .flatMap(key->redisTemplate.opsForValue().get(key));
    }
}
