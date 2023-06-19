package com.example.react.r2dbc.repository;


import com.example.react.r2dbc.model.StockInfo;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface StockInfoRepository extends ReactiveCrudRepository<StockInfo, Long> {
    @Modifying
    @Query("UPDATE stock_info s " +
            "SET s.volume = :volume, s.market_price = :marketPrice, s.change_rate = :changeRate " +
            "WHERE s.id = :id AND s.updated < :updated")
    Mono<Integer> update(Long id,
                         Long volume,
                         Integer marketPrice,
                         Double changeRate,
                         Long updated);
}
