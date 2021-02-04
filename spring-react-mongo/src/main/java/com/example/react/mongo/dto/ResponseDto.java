package com.example.react.mongo.dto;

import lombok.Builder;

@Builder
public class ResponseDto {
    String retCode;
    String msg;
    Object data;
}
