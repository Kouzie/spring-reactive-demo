package com.example.react.redis.controller;

import com.example.react.redis.dto.Sample;
import com.example.react.redis.service.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sample")
public class SampleController {

    private final ReactiveRedisTemplate<String, String> stringRedisTemplate;
    private final SampleService sampleService;

    @PostMapping
    public Mono<Void> push(@RequestBody Sample sample) {
        long id = System.currentTimeMillis() / 1000;
        return sampleService.put("sample:" + id, sample).then();
    }

    @GetMapping("/{id}")
    public Mono<Sample> getById(@PathVariable String id) {
        return sampleService.get(id);
    }

    @GetMapping
    public Flux<Sample> getAll() {
        return sampleService.getAll("sample:*");
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable String id) {
        return sampleService.delete(id).then();
    }
}
