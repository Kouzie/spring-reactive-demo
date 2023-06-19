package com.example.react.r2dbc.exception;

/**
 * 매매정보 시간충돌 예외
 * */
public class VersionConflictException extends RuntimeException {
    public VersionConflictException(String message) {
        super(message);
    }
}
