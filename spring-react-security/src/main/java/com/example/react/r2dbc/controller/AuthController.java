package com.example.react.r2dbc.controller;

import com.example.react.r2dbc.dto.MemberLoginDto;
import com.example.react.r2dbc.dto.MemberSaveDto;
import com.example.react.r2dbc.model.Member;
import com.example.react.r2dbc.repository.MemberDynamic;
import com.example.react.r2dbc.repository.MemberRepository;
import com.example.react.r2dbc.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/join")
    public Mono<Member> join(@RequestBody @Valid MemberSaveDto requestDto) {
        return memberRepository.save(Member.builder()
                .name(requestDto.getName())
                .name(requestDto.getUserName())
                .password(encoder.encode(requestDto.getPassword()))
                .build());
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Mono<ResponseEntity<?>> login(@RequestBody MemberLoginDto request) {
        return memberRepository.findByUserName(request.getUserName())
                .map(member -> {
                    if (encoder.matches(request.getPassword(), member.getPassword())) {
                        String jwtToken = jwtTokenUtil.generateToken(member);
                        Long tokenExp = jwtTokenUtil.getExpirationDateFromToken(jwtToken).getTime();
                        Map<String, Object> responseBody = new HashMap<>();
                        responseBody.put("token", jwtToken);
                        responseBody.put("token_exp", tokenExp);
                        return ResponseEntity.ok(responseBody);
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                    }
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
