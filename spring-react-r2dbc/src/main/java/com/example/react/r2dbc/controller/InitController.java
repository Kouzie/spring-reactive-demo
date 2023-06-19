package com.example.react.r2dbc.controller;

import com.example.react.r2dbc.component.InitComponent;
import com.example.react.r2dbc.component.RandomValueDto;
import com.example.react.r2dbc.dto.StockInfoDto;
import com.example.react.r2dbc.exception.NoContentException;
import com.example.react.r2dbc.service.StockInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/init")
public class InitController {
    private final InitComponent initComponent;
    private final StockInfoService service;

    /**
     * 전체 랜덤 초기화
     */
    @PostMapping("/random")
    public void randomInit() {
        service.initData();
    }

    /**
     * 단건 랜덤 초기화
     */
    @PostMapping("/random/{id}")
    public Mono<StockInfoDto> randomById(@PathVariable Long id) {
        return service.findById(id)
                .switchIfEmpty(Mono.error(new NoContentException("stock info not exist, id:" + id)))
                .flatMap(stockInfo -> {
                    RandomValueDto randomValueDto = initComponent.generateRandomValue(stockInfo.getPrice());
                    stockInfo.initial(randomValueDto.getRandomViews(),
                            randomValueDto.getRandomVolume(),
                            randomValueDto.getRandomPrice(),
                            randomValueDto.getUpdated());
                    return service.save(stockInfo);
                })
                .map(StockInfoDto::new);
    }
}
