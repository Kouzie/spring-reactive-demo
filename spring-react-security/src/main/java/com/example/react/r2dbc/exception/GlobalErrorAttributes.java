package com.example.react.r2dbc.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.react.r2dbc.exception.ErrorMessageConstant.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    private final MessageSource messageSource;

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable throwable = getError(request);
        Map<String, Object> map = new HashMap<>();
        String acceptLanguage = request.headers().firstHeader("accept-language");
        Locale locale = acceptLanguage != null && acceptLanguage.startsWith("ko") ? Locale.KOREA : Locale.getDefault();
        if (throwable instanceof AccessDeniedException) {
            map.put("code", INVALID_AUTHENTICATION_KEY_ERROR_CODE);
            map.put("error", INVALID_AUTHENTICATION_KEY_ERROR_TYPE);
        } else if (throwable instanceof AuthenticationCredentialsNotFoundException) {
            map.put("code", INVALID_AUTHENTICATION_KEY_ERROR_CODE);
            map.put("error", INVALID_AUTHENTICATION_KEY_ERROR_TYPE);
        } else if (throwable instanceof ExpiredJwtException || throwable instanceof MalformedJwtException) {
            map.put("code", INVALID_AUTHENTICATION_KEY_ERROR_CODE);
            map.put("error", INVALID_AUTHENTICATION_KEY_ERROR_TYPE);
        } else if (throwable instanceof ResponseStatusException) {
            handelResponseStatusException((ResponseStatusException) throwable, map, locale);
        } else {
            log.error("unknown server error:{}, {}" + throwable.getClass().getCanonicalName(), throwable.getMessage());
            map.put("code", UNKNOWN_ERROR_CODE);
            map.put("error", UNKNOWN_ERROR_TYPE);
        }
        return map;
    }

    private void handelResponseStatusException(ResponseStatusException throwable, Map<String, Object> map, Locale locale) {
        if (throwable.getStatus().equals(HttpStatus.NOT_FOUND)) {
            map.put("code", CONTENT_NOT_EXIST_ERROR_CODE);
            map.put("error", CONTENT_NOT_EXIST_ERROR_TYPE);
        } else {
            log.error("unknown server error:{}, {}" + throwable.getClass().getCanonicalName(), throwable.getMessage());
            map.put("code", UNKNOWN_ERROR_CODE);
            map.put("error", UNKNOWN_ERROR_TYPE);
        }
    }
}