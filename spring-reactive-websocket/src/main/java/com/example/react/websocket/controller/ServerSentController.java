package com.example.react.websocket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ServerSentController {

    private static Sinks.Many<String> many = Sinks.many().multicast().directBestEffort();

    @Scheduled(fixedDelay = 1000)
    private void sendMessage() {
        this.many.tryEmitNext("time:" + LocalDateTime.now());
    }

    @GetMapping(path = "/sse/many", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> manyStream() {
        log.info("current subscriber count:{}", many.currentSubscriberCount());
        return many.asFlux();
    }

    private static Sinks.Empty<?> empty = Sinks.empty();

    private static Sinks.One<String> one = Sinks.one();

    @GetMapping(path = "/sse/time", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> timeStream() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(i -> "Flux - " + LocalTime.now());
    }

    @GetMapping("/sse/interval")
    public Flux<ServerSentEvent<String>> intervalStream() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(i -> ServerSentEvent.builder("data " + i).build());
    }


    @GetMapping(path = "/sse/one", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> oneStream() {
        return one.asMono();
    }

    @GetMapping(path = "/sse/empty", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<?> emptyStream() {
        return empty.asMono();
    }

    @PostMapping("/add/message/{message}/type/{type}")
    public void addMessage(@PathVariable String message, @PathVariable String type) {
        if (type.equals("many"))
            many.tryEmitNext(message);
        if (type.equals("one"))
            one.tryEmitValue(message);
        if (type.equals("empty"))
            empty.tryEmitError(new IllegalArgumentException(message));
    }

}