package com.example.react.r2dbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactR2dbcApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReactR2dbcApplication.class, args);
    }

    /*@Bean
    public RouterFunction<ServerResponse> filterFunction(MemberComponent memberComponent) {
        return RouterFunctions
                .route(GET("/member/{memberId}")
                .and(accept(MediaType.APPLICATION_JSON)), memberComponent::getById)
                .filter(new ExampleHandlerFilterFunction());
    }*/
}
/*@Component
@RequiredArgsConstructor
class MemberComponent {
    private final MemberRepository memberRepository;
    public Mono<ServerResponse> getById(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue(
                       memberRepository.findById(Long.valueOf(request.pathVariable("memberId")))));
    }
}*/
