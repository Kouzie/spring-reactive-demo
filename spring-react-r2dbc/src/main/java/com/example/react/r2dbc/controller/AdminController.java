package com.example.react.r2dbc.controller;

import com.example.react.r2dbc.repository.BookRepository;
import com.example.react.r2dbc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;


}
