package com.example.react.practice;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Object value = Flux.range(0, 10)
                .flatMap(k ->
                        // Mono.subscriberContext 통해 Flux 각 요소에 대한 스트림의 공유 저장공간에 데이터 저장
                        Mono.subscriberContext().doOnNext(context -> {
                            Map map = context.get("randoms");
                            map.put(k, new Random(k).nextGaussian());
                        }).thenReturn(k))
                .publishOn(Schedulers.parallel())
                .flatMap(k ->
                        Mono.subscriberContext()
                                .map(context -> {
                                    Map map = context.get("randoms");
                                    return map.get(k);
                                }))
                .subscriberContext(context ->
                        context.put("randoms", new HashMap<>())) // 공유 저장공간인 Context 에 데이터 저장
                .blockLast(); // 마지막 값만 흭득
        System.out.println("value:" + value);
    }
}
