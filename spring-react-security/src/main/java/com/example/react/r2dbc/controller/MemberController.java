package com.example.react.r2dbc.controller;

import com.example.react.r2dbc.model.Member;
import com.example.react.r2dbc.repository.MemberDynamic;
import com.example.react.r2dbc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberDynamic memberDynamic;

    @GetMapping("/profiles")
    public Mono<Member> getProfile() {
        return ReactiveSecurityContextHolder.getContext() // Mono<SecurityContext> 반환
                .map(SecurityContext::getAuthentication)
                .flatMap(auth -> memberRepository.findByUserName(auth.getName()));
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/profiles/auth")
    public Mono<Member> getProfile(Authentication auth) {
        return memberRepository.findByName(auth.getName());
    }

    @GetMapping("/user-name-like/{userName}")
    public Flux<Member> getByUserNameLike(@PathVariable String userName) {
        return memberDynamic.findTest(userName);
    }

}
