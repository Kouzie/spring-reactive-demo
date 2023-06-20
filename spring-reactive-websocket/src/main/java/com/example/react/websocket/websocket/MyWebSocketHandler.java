package com.example.react.websocket.websocket;

import com.example.react.websocket.dto.WebSocketFromClientDto;
import com.example.react.websocket.dto.WebSocketToClientDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyWebSocketHandler implements WebSocketHandler {

    private final ObjectMapper mapper;

    @Scheduled(fixedDelay = 5000)
    public void sendTime() {
        for (Map.Entry<String, Sinks.Many<WebSocketToClientDto>> entry : WebSocketMessageSubscriber.userMap.entrySet()) {
            Sinks.Many<WebSocketToClientDto> many = entry.getValue();
            many.tryEmitNext(WebSocketToClientDto.builder()
                    .from("server")
                    .message("time:" + LocalDateTime.now())
                    .build());
        }
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        WebSocketMessageSubscriber subscriber = new WebSocketMessageSubscriber(session);
        return session.receive()
                .map(this::toDto)
                .doOnNext(subscriber::onNext)
                .doOnError(subscriber::onError)
                .doOnTerminate(subscriber::doOnTerminate)
                .zipWith(session.send(subscriber.getMany().asFlux().map(webSocketToClientDto ->
                        session.textMessage(webSocketToClientDto.getFrom() + ":" + webSocketToClientDto.getMessage()))))
                .then();
    }

    private WebSocketFromClientDto toDto(WebSocketMessage message) {
        try {
            WebSocketFromClientDto WsDto = mapper.readValue(message.getPayloadAsText(), WebSocketFromClientDto.class);
            return WsDto;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
