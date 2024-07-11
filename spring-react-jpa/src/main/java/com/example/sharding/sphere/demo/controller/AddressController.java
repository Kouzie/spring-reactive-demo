package com.example.sharding.sphere.demo.controller;


import com.example.sharding.sphere.demo.dto.AddressDto;
import com.example.sharding.sphere.demo.service.AddressService;
import com.example.sharding.sphere.demo.util.ReactUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public Flux<AddressDto> getOrders() {
        return ReactUtil.blockingToFlux(() -> addressService.getAllAddress());
    }

    @PostMapping
    public Mono<AddressDto> addRandomAddress() {
        return ReactUtil.blockingToMono(() -> addressService.addRandomAccount());
    }
}
