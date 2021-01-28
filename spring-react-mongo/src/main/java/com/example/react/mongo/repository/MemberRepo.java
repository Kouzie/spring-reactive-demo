package com.example.react.mongo.repository;

import com.example.react.mongo.model.Member;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MemberRepo extends ReactiveMongoRepository<Member, ObjectId> {
    Mono<Member> findByName(String name);
    Mono<Member> findByUserName(String name);
}
