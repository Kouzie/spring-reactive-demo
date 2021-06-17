package com.example.react.websocket.client.websocket;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.socket.WebSocketSession;

@Slf4j
public class WebSocketMessageSubscriber {
    private final String id;

    public WebSocketMessageSubscriber(WebSocketSession session) {
        id = session.getId();
    }

    public void onNext(String msg) {
        log.info("onNext invoked, msg:{}", msg);
    }

    public void onError(Throwable error) {
        //TODO log error
        log.error("onError invoked, error:{}, {}", error.getClass().getSimpleName(), error.getMessage());
    }

    public void doOnTerminate() {
        log.info("doOnTerminate invoked, id:{}", id);
    }

}