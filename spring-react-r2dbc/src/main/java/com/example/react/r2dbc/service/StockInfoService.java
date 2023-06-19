package com.example.react.r2dbc.service;


import com.example.react.r2dbc.component.InitComponent;
import com.example.react.r2dbc.dto.SortOrder;
import com.example.react.r2dbc.dto.SortTag;
import com.example.react.r2dbc.dto.StockInfoDto;
import com.example.react.r2dbc.exception.VersionConflictException;
import com.example.react.r2dbc.model.StockInfo;
import com.example.react.r2dbc.repository.StockInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.CriteriaDefinition;
import org.springframework.data.relational.core.query.Query;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockInfoService {
    private final InitComponent initComponent;
    private final StockInfoRepository repository;
    private final R2dbcEntityTemplate entityTemplate;

    @Autowired
    @Qualifier("snakeCaseObjectMapper")
    private ObjectMapper snakeCaseObjectMapper;

    public void initData() {
        List<StockInfoDto> initData = initComponent.initData();
        List<StockInfo> stockInfos = initData.stream()
                .map(StockInfo::toEntity)
                .collect(Collectors.toList());
        for (StockInfo stockInfo : stockInfos) {
            repository.findById(stockInfo.getId())
                    .flatMap(info -> {
                        info.initial(stockInfo.getViews(), stockInfo.getVolume(), stockInfo.getMarketPrice(), stockInfo.getUpdated());
                        return repository.save(info);
                    })
                    .switchIfEmpty(entityTemplate.insert(stockInfo))
                    .subscribe();
        }
    }

    public Flux<StockInfo> getDashboard(Integer page, Integer size, SortTag tag, SortOrder order) {
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("SELECT s.* FROM stock_info s ");
        mainQuery.append("JOIN  (");

        StringBuilder subQuery = new StringBuilder();
        subQuery.append("SELECT id FROM stock_info ");
        if (tag == SortTag.VIEWS) {
            subQuery.append("ORDER BY views DESC ");
        } else if (tag == SortTag.VOLUME) {
            subQuery.append("ORDER BY volume DESC ");
        } else if (tag == SortTag.RATE) {
            if (order == SortOrder.DESC)
                subQuery.append("ORDER BY change_rate DESC ");
            else
                subQuery.append("ORDER BY change_rate ASC ");
        }
        subQuery.append("LIMIT :limit OFFSET :offset");
        mainQuery.append(subQuery);
        mainQuery.append(") AS sub_s ON s.id = sub_s.id");
        DatabaseClient.GenericExecuteSpec spec = entityTemplate.getDatabaseClient()
                .sql(mainQuery.toString())
                .bind("limit", size)
                .bind("offset", (page - 1) * size);
        return spec.fetch().all().map(m -> snakeCaseObjectMapper.convertValue(m, StockInfo.class));
    }

    public Flux<StockInfo> getDashboard2(Integer page, Integer size, SortTag tag, SortOrder order) {
        Query query = Query.query(CriteriaDefinition.empty());
        if (tag == SortTag.VIEWS) {
            query = query.sort(Sort.by(Sort.Direction.DESC, "views"));
        } else if (tag == SortTag.VOLUME) {
            query = query.sort(Sort.by(Sort.Direction.DESC, "volume"));
        } else if (tag == SortTag.RATE) {
            if (order == SortOrder.DESC)
                query = query.sort(Sort.by(Sort.Direction.DESC, "changeRate"));
            else
                query = query.sort(Sort.by(Sort.Direction.ASC, "changeRate"));
        }
        query = query.limit(size)
                .offset(page - 1);
        Flux<StockInfo> stockInfos = entityTemplate.select(StockInfo.class)
                .matching(query)
                .all();
        return stockInfos;
    }

    public Mono<StockInfo> findById(Long id) {
        return repository.findById(id);
    }

    public Mono<StockInfo> save(StockInfo stockInfo) {
        if (stockInfo.getId() == null)
            return repository.save(stockInfo);
        else {
            return repository.update(
                            stockInfo.getId(),
                            stockInfo.getVolume(),
                            stockInfo.getMarketPrice(),
                            stockInfo.getChangeRate(),
                            stockInfo.getUpdated())
                    .flatMap(updateResult -> {
                        if (updateResult < 1) {
                            return Mono.error(new VersionConflictException("date time conflict, date:" + stockInfo.getUpdated()));
                        }
                        return Mono.just(stockInfo);
                    });
        }
    }
}
