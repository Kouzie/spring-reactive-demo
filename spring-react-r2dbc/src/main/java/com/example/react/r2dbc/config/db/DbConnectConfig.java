package com.example.react.r2dbc.config.db;

import com.example.react.r2dbc.model.Member;
import com.example.react.r2dbc.repository.MemberRepository;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;
import java.util.Arrays;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DbConnectConfig {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        // h2 이외의 Db 연결시에는 ConnectionFactory 를 별도로 지정해주어야함.
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        //initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        return initializer;
    }

    //@Bean
    public CommandLineRunner demo() {
        return (args) -> {
            // save a few customers
            memberRepository.saveAll(Arrays.asList(
                    Member.builder().name("Chloe").password(encoder.encode("Chloe")).userName("Chloe@test.com").build(),
                    Member.builder().name("Kim").password(encoder.encode("Kim")).userName("Kim@test.com").build(),
                    Member.builder().name("David").password(encoder.encode("David")).userName("David@test.com").build(),
                    Member.builder().name("Michelle").password(encoder.encode("Michelle")).userName("Michelle@test.com").build()
            )).blockLast(Duration.ofSeconds(10));

            // fetch all customers
            log.info("Member found with findAll():");
            log.info("-------------------------------");
            memberRepository.findAll().doOnNext(member -> {
                log.info(member.toString());
            }).blockLast(Duration.ofSeconds(10));

            // fetch an individual customer by ID
            memberRepository.findById(1L).doOnNext(member -> {
                log.info("Member found with findById(1L):");
                log.info("--------------------------------");
                log.info(member.toString());
                log.info("");
            }).block(Duration.ofSeconds(10));

            // fetch customers by last name
            log.info("Member found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            memberRepository.findByName("Bauer").doOnNext(member -> {
                log.info(member.toString());
            }).block(Duration.ofSeconds(10));
        };
    }


}
