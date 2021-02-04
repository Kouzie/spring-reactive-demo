package com.example.rsocket.client.controller;

import com.example.rsocket.client.dto.ClientHealthState;
import io.rsocket.metadata.WellKnownMimeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.rsocket.metadata.UsernamePasswordMetadata;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@Slf4j
@Controller
public class AcceptorController {

    @MessageMapping("health")
    Flux<ClientHealthState> healthy() {
        log.info("health invoked");
        Stream<ClientHealthState> stream = Stream.generate(() -> new ClientHealthState(Math.random() > 0.2));
        return Flux.fromStream(stream).delayElements(Duration.ofSeconds(1));
    }
}
