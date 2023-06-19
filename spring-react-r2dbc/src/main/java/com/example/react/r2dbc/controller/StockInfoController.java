package com.example.react.r2dbc.controller;


import com.example.react.r2dbc.dto.SortOrder;
import com.example.react.r2dbc.dto.SortTag;
import com.example.react.r2dbc.dto.StockInfoDto;
import com.example.react.r2dbc.model.StockInfo;
import com.example.react.r2dbc.service.StockInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockInfoController {
    private final StockInfoService service;

    /**
     * 증권 대시보드 조회
     */
    @GetMapping("/dashboard")
    public Flux<StockInfoDto> getStockList(
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "10") @Min(10) @Max(50) Integer size,
            @RequestParam(defaultValue = "views") String tag,
            @RequestParam(defaultValue = "desc") String order
    ) {
        SortTag sortTag = SortTag.keyOf(tag);
        SortOrder sortOrder = SortOrder.keyOf(order);
        Flux<StockInfo> result = service.getDashboard2(page, size, sortTag, sortOrder);
        return result.map(StockInfoDto::new);
    }
}
