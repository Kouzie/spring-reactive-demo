package com.example.react.r2dbc.repository;

import com.example.react.r2dbc.dto.RentDetail;
import com.example.react.r2dbc.model.Book;
import com.example.react.r2dbc.model.Rent.Rent;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RentRepository extends ReactiveCrudRepository<Rent, Long> {

    @Query("SELECT b.* FROM rent r LEFT JOIN book b ON r.book_id = b.id WHERE r.member_id = :memberId")
    public Flux<Book> findAllBookByMemberRent(@Param("memberId") Long memberId);

    @Query("SELECT r.id rent_id, m.id member_id, b.id book_id, m.name name, b.title title FROM rent r " +
            "LEFT JOIN member m ON r.member_id = m.id " +
            "LEFT JOIN book b ON r.book_id = b.id " +
            "WHERE r.id = :rentId")
    Flux<RentDetail> findDetailBy(@Param("rentId") Long rentId);
}
