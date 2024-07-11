package com.example.sharding.sphere.demo.util;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.function.Supplier;

public class ReactUtil {

    public static <T> Flux<T> blockingToFlux(Supplier<List<T>> supplier) {
        return Flux.defer(() -> Flux.fromIterable(supplier.get()))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public static <T> Mono<T> blockingToMono(Supplier<T> supplier) {
        return Mono.fromSupplier(supplier)
                .subscribeOn(Schedulers.boundedElastic());
    }
}
