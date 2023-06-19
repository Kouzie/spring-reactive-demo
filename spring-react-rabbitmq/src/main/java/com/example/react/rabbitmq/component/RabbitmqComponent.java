package com.example.react.rabbitmq.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitmqComponent {
    private final AmqpTemplate amqpTemplate;

    public Mono<Boolean> convertAndSend(String message) {
        Mono<Boolean> mono = Mono.fromCallable(() -> {
            amqpTemplate.convertAndSend(message);
            return true;
        }).subscribeOn(Schedulers.parallel());
        return mono;
    }

    @RabbitListener( // <1>
            ackMode = "MANUAL", //
            bindings = @QueueBinding( // <2>
                    value = @Queue, // <3>
                    exchange = @Exchange("hacking-spring-boot"), // <4>
                    key = "new-items-spring-amqp")) // <5>
    public Mono<String> processNewItemsViaSpringAmqp(String message) { // <6>
        return Mono.just(message); // <7>
    }
}
