package com.example.rsocket.server.controller;

import com.example.rsocket.server.dto.ClientHealthState;
import com.example.rsocket.server.dto.GreetingRequest;
import com.example.rsocket.server.dto.GreetingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@Slf4j
@Controller
public class GreetingController {


    /**
     * fire and forget 0:1 (single value in and no response)
     * request response 1:1 (single value in and single value out)
     * request stream 1:N(single value in and multi value out)
     * channel N:N (multi value in and multi value out)
     * <p>
     * https://github.com/making/rsc
     * using Rsocket connection cli tool test my RSocket Server
     * brew install making/tap/rsc
     */
    //@MessageMapping("greetings")
    private Flux<GreetingResponse> greet(RSocketRequester client, GreetingRequest request) {
        // this pattern is request stream pattern
        Flux in = client.route("health")
                .retrieveFlux(ClientHealthState.class)
                .filter(clientHealthState -> !clientHealthState.isHealthy())
                .doOnNext(chs -> log.info("not healthy! "));

        Stream<GreetingResponse> stream = Stream.generate(() -> new GreetingResponse("hello " + request.getMessage() + " @ " + Instant.now() + "!"));
        Flux out = Flux.fromStream(stream)
                .takeUntilOther(in)
                .delayElements(Duration.ofSeconds(1));
        return out;
    }


    @MessageMapping("greetings")
    Flux<GreetingResponse> greet(RSocketRequester client, @AuthenticationPrincipal UserDetails userDetails) {
        Flux in = client.route("health")
                .retrieveFlux(ClientHealthState.class)
                .filter(clientHealthState -> !clientHealthState.isHealthy())
                .doOnNext(chs -> log.info("not healthy! "));

        Stream<GreetingResponse> stream = Stream.generate(() -> new GreetingResponse("hello " + userDetails.getUsername() + " @ " + Instant.now() + "!"));
        Flux out = Flux.fromStream(stream)
                .takeUntilOther(in)
                .delayElements(Duration.ofSeconds(1));
        return out;
    }
}
