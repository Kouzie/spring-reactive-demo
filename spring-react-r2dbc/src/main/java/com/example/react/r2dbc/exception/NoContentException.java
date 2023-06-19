package com.example.react.r2dbc.exception;

/**
 * DB 조회 DATA 없을 경우 예외
 */
public class NoContentException extends RuntimeException {
    public NoContentException(String message) {
        super(message);
    }
}
