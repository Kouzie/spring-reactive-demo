package com.example.react.websocket.client.controller;

import com.example.react.websocket.client.adapter.WebSocketAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final WebSocketAdapter webSocketAdapter;

    @PostMapping("/{id}/{message}")
    public String sendMessage(@PathVariable String id, @PathVariable String message) {
        webSocketAdapter.sendMessage(id, message);
        return "success";
    }
}
