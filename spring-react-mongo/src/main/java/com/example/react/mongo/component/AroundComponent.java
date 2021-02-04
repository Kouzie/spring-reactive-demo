package com.example.react.mongo.component;

import com.example.react.mongo.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@Slf4j
public class AroundComponent {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restController() {
    }

    @Around("restController()")
    public Mono aroundTargetMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        ServerRequest serverRequest = ServerRequest.class.cast(proceedingJoinPoint.getArgs()[0]);
        log.info(methodName + "invoked" + serverRequest.methodName() + "," + serverRequest.uri());
        Mono result = (Mono) proceedingJoinPoint.proceed();
        result.map(ret -> ResponseDto.builder()
                .data(ret)
                .retCode("0000")
                .data(ret));
        return result;
    }
}
