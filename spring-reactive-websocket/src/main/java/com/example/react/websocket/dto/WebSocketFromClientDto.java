package com.example.react.websocket.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketFromClientDto {
    private String to;
    private String message; // str
}
