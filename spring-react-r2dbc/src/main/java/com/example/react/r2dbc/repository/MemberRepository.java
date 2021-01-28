package com.example.react.r2dbc.repository;

import com.example.react.r2dbc.model.Member;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<Member, Long> {
    Mono<Member> findByName(String name);

    Mono<Member> findByUserName(String name);

    @Query("SELECT * FROM member WHERE name = :name AND user_name = :userName")
    Mono<Member> findByNameAndUserName(String name, String userName);
}