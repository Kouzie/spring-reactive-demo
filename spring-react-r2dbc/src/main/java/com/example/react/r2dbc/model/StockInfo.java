package com.example.react.r2dbc.model;


import com.example.react.r2dbc.dto.StockInfoDto;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table("stock_info")
public class StockInfo {
    @Id
    private Long id;
    private String code;
    private String name;
    private Integer price; // 시작가
    private Integer views; // 조회수
    private Long volume; // 거래량
    private Integer marketPrice; // 시장가
    private Double changeRate; // 변동률, -0.3 ~ +0.3

    private Long updated;

    protected StockInfo() {

    }

    /**
     * 기본 정보 초기화
     */
    public StockInfo(Long id, String code, String name, Integer price) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
        this.updated = System.currentTimeMillis();
    }

    /**
     * 추가정보 업데이트
     */
    public void initial(Integer views, Long volume, Integer marketPrice, Long updated) {
        this.views = views;
        this.volume = volume;
        this.marketPrice = marketPrice;
        this.changeRate = calcRate(this.price, marketPrice);
        this.updated = updated;
    }

    /**
     * 시장가 업데이트시 rate 항시 업데이트 진행
     */
    public void updateMarketPrice(Integer marketPrice) {
        this.marketPrice = marketPrice;
        this.changeRate = calcRate(this.price, marketPrice);
    }

    public static Double calcRate(Integer price, Integer marketPrice) {
        double rate = (double) (marketPrice - price) / price;
        rate = Math.floor(rate * 10000) / 100;
        return rate;
    }

    public static StockInfo toEntity(StockInfoDto dto) {
        StockInfo entity = new StockInfo();
        entity.id = dto.getId();
        entity.code = dto.getCode();
        entity.name = dto.getName();
        entity.price = dto.getPrice();
        entity.views = dto.getViews();
        entity.volume = dto.getVolume();
        entity.marketPrice = dto.getMarketPrice();
        entity.changeRate = dto.getChangeRate();
        entity.updated = dto.getUpdated();
        return entity;
    }
}
