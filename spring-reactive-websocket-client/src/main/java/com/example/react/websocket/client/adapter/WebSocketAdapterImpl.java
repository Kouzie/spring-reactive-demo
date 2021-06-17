package com.example.react.websocket.client.adapter;

import com.example.react.websocket.client.dto.WebSocketFromClientDto;
import com.example.react.websocket.client.websocket.WebSocketMessageSubscriber;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import javax.annotation.PostConstruct;
import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketAdapterImpl implements WebSocketHandler , WebSocketAdapter {

    private final WebSocketClient client = new ReactorNettyWebSocketClient();
    private Sinks.Many<WebSocketFromClientDto> many;

    @PostConstruct
    private void connectClient() {
        log.info("connectClient invoked");
        many = Sinks.many().unicast().onBackpressureBuffer();
        client.execute(URI.create("ws://localhost:8080/ws/chat"), this).subscribe();
    }

    @Override
    public void sendMessage(String to, String message) {
        many.tryEmitNext(WebSocketFromClientDto.builder().to(to).message(message).build());
    }


    @Override
    public Mono<Void> handle(WebSocketSession session) {
        WebSocketMessageSubscriber subscriber = new WebSocketMessageSubscriber(session);
        return session.receive()
                .map(webSocketMessage -> webSocketMessage.getPayloadAsText())
                .doOnNext(subscriber::onNext)
                .doOnError(subscriber::onError)
                .doOnCancel(() -> {
                    subscriber.doOnTerminate();
                    connectClient();
                })
                .zipWith(session.send(many.asFlux().map(webSocketFromClientDto ->
                        session.textMessage("test erorr"))))
                .then();
    }
}
