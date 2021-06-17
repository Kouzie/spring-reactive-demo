package com.example.react.websocket.websocket;

import com.example.react.websocket.dto.WebSocketFromClientDto;
import com.example.react.websocket.dto.WebSocketToClientDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatSocketHandler implements WebSocketHandler {

    private final ObjectMapper mapper;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        WebSocketMessageSubscriber subscriber = new WebSocketMessageSubscriber(session);
        return session.receive()
                .map(this::toDto)
                .doOnNext(subscriber::onNext)
                .doOnCancel(subscriber::onCancel)
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

@Slf4j
class WebSocketMessageSubscriber {
    public static Map<String, Sinks.Many<WebSocketToClientDto>> userMap = new HashMap<>(); //sessionId, sink
    private final String id;
    @Getter
    private final Sinks.Many<WebSocketToClientDto> many;

    public WebSocketMessageSubscriber(WebSocketSession session) {
        many = Sinks.many().unicast().onBackpressureBuffer();
        id = session.getId();
        many.tryEmitNext(WebSocketToClientDto.builder().from("system").message("welcome, " + id).build());
        userMap.put(id, many);
    }

    public void onNext(WebSocketFromClientDto msg) {
        log.info("onNext invoked, to:{}, msg:{}", msg.getTo(), msg.getMessage());
        Sinks.Many<WebSocketToClientDto> to = userMap.get(msg.getTo());
        if (to == null)
            many.tryEmitNext(WebSocketToClientDto.builder().from("system").message("no user:" + msg.getTo()).build());
        else
            to.tryEmitNext(WebSocketToClientDto.builder().from(id).message(msg.getMessage()).build());
    }

    public void onError(Throwable error) {
        //TODO log error
        log.error("onError invoked, error:{}, {}", error.getClass().getSimpleName(), error.getMessage());
        many.tryEmitNext(WebSocketToClientDto.builder()
                .from("system")
                .message(id + " on error, error:" + error.getMessage())
                .build());
        userMap.remove(id);
    }

    public void onCancel() {
        log.info("onCancel invoked, id:{}", id);
        userMap.remove(id);
        for (Map.Entry<String, Sinks.Many<WebSocketToClientDto>> entry : userMap.entrySet()) {
            if (!entry.getKey().equals(id))
                entry.getValue().tryEmitNext(WebSocketToClientDto.builder()
                        .from("system")
                        .message(id + " is exit")
                        .build());
        }
    }

}