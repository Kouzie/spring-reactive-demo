package com.example.react.r2dbc.repository;

import com.example.react.r2dbc.model.Book;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, Long> {

    @Query("SELECT * FROM book WHERE publishing_year = " +
            "(SELECT MAX(publishing_year) FROM book)")
    Flux<Book> findTheLatestBooks();

    Flux<Book> findByTitleRegex(String regex);
}
