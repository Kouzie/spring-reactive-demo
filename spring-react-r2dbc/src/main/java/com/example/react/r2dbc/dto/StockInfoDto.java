package com.example.react.r2dbc.dto;


import com.example.react.r2dbc.component.RandomValueDto;
import com.example.react.r2dbc.model.StockInfo;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StockInfoDto {
    private Long id;
    private String code;
    private String name;
    private Integer price; // 시작가
    private Integer views; // 조회수
    private Long volume; // 거래량
    private Integer marketPrice; // 시장가
    private Double changeRate; // 변동률, min -30.0 ~ +30.0
    private Long updated;

    public StockInfoDto(StockInfo stockInfo) {
        id = stockInfo.getId();
        code = stockInfo.getCode();
        name = stockInfo.getName();
        price = stockInfo.getPrice();
        views = stockInfo.getViews();
        volume = stockInfo.getVolume();
        marketPrice = stockInfo.getMarketPrice();
        changeRate = stockInfo.getChangeRate();
        updated = stockInfo.getUpdated();
    }

    public void update(RandomValueDto randomValueDto) {
        views = randomValueDto.getRandomViews();
        volume = randomValueDto.getRandomVolume();
        marketPrice = randomValueDto.getRandomPrice();
        double rate = (double) (marketPrice - price) / price;
        rate = Math.floor(rate * 10000) / 100;
        changeRate = rate;
        updated = randomValueDto.getUpdated();
    }
}
