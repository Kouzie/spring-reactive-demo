package com.example.sharding.sphere.demo.service;

import com.example.sharding.sphere.demo.dto.OrderDto;
import com.example.sharding.sphere.demo.repository.*;
import com.example.sharding.sphere.demo.util.RandomTestUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AccountRepository accountRepository;

    @PostConstruct
    private void init() {
        List<AccountEntity> accountEntityList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AccountEntity account = new AccountEntity(
                    RandomTestUtil.generateRandomString(16));
            AccountEntity accountEntity = accountRepository.save(account);
            accountEntityList.add(accountEntity);
        }
        for (int i = 0; i < 20; i++) {
            int randomIndex = RandomTestUtil.generateRandomInteger(accountEntityList.size());
            AccountEntity accountEntity = accountEntityList.get(randomIndex);
            Long accountId = accountEntity.getAccountId();
            OrderEntity orderEntity = new OrderEntity(
                    RandomTestUtil.generateRandomString(10),
                    accountId);
            orderEntity = orderRepository.save(orderEntity);
            int itemCnt = RandomTestUtil.generateRandomInteger(5);
            for (int j = 0; j < itemCnt; j++) {
                OrderItemEntity orderItemEntity = new OrderItemEntity(
                        orderEntity.getOrderId(),
                        accountId,
                        RandomTestUtil.generateRandomString(10)
                );
                orderItemRepository.save(orderItemEntity);
            }
        }
    }

    @Transactional
    public OrderDto addRandomOrder(Long accountId) {
        OrderEntity entity = new OrderEntity(
                RandomTestUtil.generateRandomString(10),
                accountId);
        // final Object lock = new Object();
        // System.out.println("Start");
        // synchronized (lock) {
        //     try {
        //         lock.wait(1000); // 2초 동안 대기
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }
        entity = orderRepository.save(entity);
        return toDto(entity);
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getAllTables(Long accountId) {
        return orderRepository.findAllByAccountId(accountId).stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderDto getTable(Long id) {
        return orderRepository.findById(id)
                .map(this::toDto)
                .orElseThrow();
    }

    @Transactional
    public OrderDto changeOrderStatusRunning(Long id) {
        OrderEntity order = orderRepository.findByIdForUpdate(id)
                .orElseThrow();
        if (order.getStatus() == null || order.getStatus().equals("created")) {
            order.updateStatus("running");
            order = orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("invalid order, id: " + id);
        }
        return toDto(order);
    }

    @Transactional
    public OrderDto changeOrderStatusComplete(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow();
        order.updateStatus("complete");
        order = orderRepository.save(order);
        return toDto(order);
    }

    private OrderDto toDto(OrderEntity entity) {
        OrderDto dto = new OrderDto();
        dto.setOrderId(entity.getOrderId());
        dto.setTitle(entity.getTitle());
        dto.setAccountId(entity.getAccountId());
        dto.setCreateTime(entity.getCreateTime());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
