package com.example.react.mongo.controller;

import com.example.react.mongo.dto.MemberLoginDto;
import com.example.react.mongo.dto.MemberSaveDto;
import com.example.react.mongo.model.Member;
import com.example.react.mongo.repository.MemberRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepo memberRepo;
    private final PasswordEncoder encoder;

    @GetMapping("/id/{id}")
    public Mono<Member> getById(@PathVariable String id) {
        return memberRepo.findById(new ObjectId(id));
    }

    @GetMapping("/name/{name}")
    public Mono<Member> getByName(@PathVariable String name) {
        return memberRepo.findByName(name);
    }

    @GetMapping("/user-name/{userName}")
    public Mono<Member> getByUserName(@PathVariable String userName) {
        return memberRepo.findByName(userName);
    }

    @PostMapping("/join")
    public Mono<Member> join(@RequestBody @Valid MemberSaveDto requestDto) {
        return memberRepo.save(Member.builder()
                .name(requestDto.getName())
                .name(requestDto.getUserName())
                .password(encoder.encode(requestDto.getPassword()))
                .build());
    }

    @PostMapping("/login")
    public Mono<Member> login(@RequestBody @Valid MemberLoginDto requestDto) {
        return memberRepo.findByUserName(requestDto.getUserName())
                .map(member-> {
                    if( encoder.matches(requestDto.getPassword(), member.getPassword()))
                        return member;
                    else
                        throw new IllegalArgumentException("login err");
                });
    }
}
