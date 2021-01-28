package com.example.react.redis.service;

import com.example.react.redis.dto.Sample;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SampleService {
    private final ReactiveRedisTemplate<String, Sample> redisTemplate;

    public Mono<Boolean> put(String key, Sample sample) {
        return redisTemplate.opsForValue().set(key, sample);
    }

    public Mono<Sample> get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Flux<Sample> getAll(String keyPattern){
        return redisTemplate.keys(keyPattern)
                .flatMap(key-> redisTemplate.opsForValue().get(key));
    }

    public Mono<Boolean> delete(String key) {
        return redisTemplate.opsForValue().delete(key);
    }
}
