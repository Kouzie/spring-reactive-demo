package com.example.sharding.sphere.demo.repository;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

import java.time.OffsetDateTime;

@Getter
@Entity
@DynamicUpdate
@Table(name = "t_order")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 기본적으로 GenerationType.IDENTITY를 사용합니다.
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "account_id")
    private Long accountId;
    private String title;
    private String status;
    private OffsetDateTime createTime;

    protected OrderEntity() {
    }

    public OrderEntity(String title, Long accountId) {
        this.title = title;
        this.accountId = accountId;
        this.createTime = OffsetDateTime.now();
        this.status = "created";
    }

    public void updateStatus(String status) {
        this.status = status;
    }
}
