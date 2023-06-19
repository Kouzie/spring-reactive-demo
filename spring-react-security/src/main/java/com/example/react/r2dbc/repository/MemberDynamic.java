package com.example.react.r2dbc.repository;

import com.example.react.r2dbc.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static org.springframework.data.relational.core.query.Criteria.where;

@Service
@RequiredArgsConstructor
public class MemberDynamic {
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public Flux<Member> findTest(String userName) {
        Query query = Query.query(where("user_name").like("%" + userName + "%"))
                .limit(10)
                .offset(0);
        return r2dbcEntityTemplate.select(Member.class)
                .matching(query)
                .all();
    }
}
