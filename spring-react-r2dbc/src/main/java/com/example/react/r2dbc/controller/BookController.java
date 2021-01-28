package com.example.react.r2dbc.controller;

import com.example.react.r2dbc.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    // 작년 추가된 책 권수
    @GetMapping("/last")
    public Long findTheLatestBooks() {
        return bookRepository.findTheLatestBooks()
                .doOnNext(book -> log.info("Book: {}", book))
                .count()
                .doOnSuccess(count -> log.info("Database contains {} latest books", count))
                .block();
    }
}
