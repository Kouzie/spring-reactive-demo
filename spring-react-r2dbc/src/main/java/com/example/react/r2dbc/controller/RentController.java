package com.example.react.r2dbc.controller;

import com.example.react.r2dbc.dto.RentDetail;
import com.example.react.r2dbc.model.Book;
import com.example.react.r2dbc.repository.BookRepository;
import com.example.react.r2dbc.repository.MemberRepository;
import com.example.react.r2dbc.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/rent")
@RequiredArgsConstructor
public class RentController {
    private final RentRepository rentRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    @GetMapping("/{rentId}")
    public Flux<RentDetail> getRentById(@PathVariable Long rentId) {
        Flux<RentDetail> rent = rentRepository.findDetailBy(rentId);
        return rent;
    }

    @GetMapping("/member-id/{memberId}")
    public Flux<Book> getAllRentByMember(@PathVariable Long memberId) {
        return rentRepository.findAllBookByMemberRent(memberId);
    }
}
