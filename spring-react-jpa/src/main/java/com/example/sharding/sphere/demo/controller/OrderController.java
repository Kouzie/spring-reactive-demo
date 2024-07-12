package com.example.sharding.sphere.demo.controller;

import com.example.sharding.sphere.demo.dto.AccountDto;
import com.example.sharding.sphere.demo.dto.OrderDto;
import com.example.sharding.sphere.demo.service.AccountService;
import com.example.sharding.sphere.demo.service.OrderService;
import com.example.sharding.sphere.demo.util.ReactUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final AccountService accountService;

    @GetMapping("/account-id/{accountId}")
    public Flux<OrderDto> getOrders(@PathVariable Long accountId) {
        return ReactUtil.blockingToFlux(() -> orderService.getAllTables(accountId));
    }

    @GetMapping("/{id}")
    public Mono<OrderDto> getOrder(@PathVariable Long id) {
        return ReactUtil.blockingToMono(() -> orderService.getTable(id));
    }

    @PutMapping("/status/{id}")
    public Mono<OrderDto> updateStatus(@PathVariable Long id) {
        Mono<OrderDto> changeStatusRunning = ReactUtil.blockingToMono(() -> orderService.changeOrderStatusRunning(id));
        Mono<Long> doSomethingDeploy = Mono.delay(Duration.ofSeconds(1))
                .doOnNext(t -> System.out.println("timeover"));
        Mono<OrderDto> changeStatusComplete = ReactUtil.blockingToMono(() -> orderService.changeOrderStatusComplete(id));
        return changeStatusRunning
                .then(doSomethingDeploy)
                .then(changeStatusComplete);
    }

    @PostMapping
    public Mono<OrderDto> addRandomOrder() {
        Mono<OrderDto> result = ReactUtil.blockingToMono(() -> {
            AccountDto accountDto = accountService.getRandomAccount();
            return orderService.addRandomOrder(accountDto.getAccountId());
        });
        return  Mono.delay(Duration.ofSeconds(1))
                .doOnNext(t -> System.out.println("timeover"))
                .then(result);
    }
}
