package com.example.sharding.sphere.demo.controller;

import com.example.sharding.sphere.demo.dto.AccountDto;
import com.example.sharding.sphere.demo.service.AccountService;
import com.example.sharding.sphere.demo.util.ReactUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService service;

    @GetMapping("/{accountId}")
    public Mono<AccountDto> getAccount(@PathVariable Long accountId) {
        return ReactUtil.blockingToMono(() -> service.getAccountById(accountId));
    }

    @PostMapping
    public Mono<AccountDto> addRandomAccount() {
        return ReactUtil.blockingToMono(() -> service.addRandomAccount());
    }
}
