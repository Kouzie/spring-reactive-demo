package com.example.react.r2dbc.config;

import com.example.react.r2dbc.exception.NoContentException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import javax.validation.ConstraintViolationException;
import java.util.Map;

/*
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<ErrorResponseDto> noContentException(NoContentException e, HttpServletRequest request) {
        ErrorResponseDto ret = ErrorResponseDto.builder()
                .msg(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        e.printStackTrace();
        return new ResponseEntity<>(ret, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> constraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        ErrorResponseDto ret = ErrorResponseDto.builder()
                .msg(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        e.printStackTrace();
        return new ResponseEntity<>(ret, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> exception(Exception e, HttpServletRequest request) {
        ErrorResponseDto ret = ErrorResponseDto.builder()
                .msg(e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        e.printStackTrace();
        return new ResponseEntity<>(ret, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}*/


@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    private final ObjectMapper objectMapper;

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable throwable = getError(request);
        log.error("error message:{}", throwable.getMessage());
        ErrorResponseDto responseDto = new ErrorResponseDto();
        responseDto.setMsg(throwable.getMessage());
        if (throwable instanceof NoContentException) {
            responseDto.setStatus(HttpStatus.NOT_FOUND.value());
        } else if (throwable instanceof ConstraintViolationException) {
            responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
        } else {
            responseDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return objectMapper.convertValue(responseDto, new TypeReference<Map<String, Object>>() {
        });
    }


    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorResponseDto {
        private String msg;
        private Integer status;
    }
}