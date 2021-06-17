package com.example.react.websocket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WebSocketToClientDto {
    private String from;
    private String message;
}
