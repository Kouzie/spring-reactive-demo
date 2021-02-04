package com.example.rsocket.client.config;

import com.example.rsocket.client.controller.AcceptorController;
import com.example.rsocket.client.dto.GreetingRequest;
import com.example.rsocket.client.dto.GreetingResponse;
import io.rsocket.SocketAcceptor;
import io.rsocket.metadata.WellKnownMimeType;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.security.rsocket.metadata.SimpleAuthenticationEncoder;
import org.springframework.security.rsocket.metadata.UsernamePasswordMetadata;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

@Configuration
public class ClientConfig {

    private final UsernamePasswordMetadata credentials = new UsernamePasswordMetadata("kouzie", "password");
    private final MimeType mimeType = MimeTypeUtils.parseMimeType(WellKnownMimeType
            .MESSAGE_RSOCKET_AUTHENTICATION
            .getString());

    @Bean
    RSocketStrategiesCustomizer rSocketStrategiesCustomizer() {
        return strategies -> strategies.encoder(new SimpleAuthenticationEncoder());
        // AUTHENTICATION_MIME_TYPE 을 사용해 데이터를 인코딩
    }

    @Bean
    SocketAcceptor socketAcceptor(RSocketStrategies strategies,
                                  AcceptorController controller) {
        return RSocketMessageHandler.responder(strategies, controller);
    }

    @Bean
    RSocketRequester rSocketRequester(RSocketRequester.Builder builder,
                                      SocketAcceptor socketAcceptor) {
        return builder
                .setupMetadata(credentials, mimeType)
                .rsocketConnector(connector -> connector.acceptor(socketAcceptor))
                .tcp("localhost", 8888);
    }


    @Bean
    ApplicationListener<ApplicationReadyEvent> client(RSocketRequester client) {
        return new ApplicationListener<ApplicationReadyEvent>() {
            @Override
            public void onApplicationEvent(ApplicationReadyEvent event) {
                client.route("greetings")
                        // .metadata(credentials, mimeType) // 직접 설정도 가능하다.
                        .data(new GreetingRequest("kouzie"))
                        .retrieveFlux(GreetingResponse.class)
                        .subscribe(System.out::println);
            }
        };
    }

}
