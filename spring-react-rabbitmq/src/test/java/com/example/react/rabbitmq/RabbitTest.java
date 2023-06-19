package com.example.react.rabbitmq;

import com.example.react.rabbitmq.model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;

/**
 * @author Greg Turnquist
 */
@SpringBootTest
@Testcontainers
@ContextConfiguration
@AutoConfigureWebTestClient
public class RabbitTest {

    @Container
    static RabbitMQContainer container = new RabbitMQContainer("rabbitmq:3.7.25-management-alpine"); // <5>

    @Autowired
    WebTestClient webTestClient; // <6>

    @DynamicPropertySource // <8>
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", container::getContainerIpAddress);
        registry.add("spring.rabbitmq.port", container::getAmqpPort);
    }
    // end::setup[]

    // tag::spring-amqp-test[]
    @Test
    void verifyMessagingThroughAmqp() throws InterruptedException {
        this.webTestClient.post().uri("/test")
                .bodyValue(new Item("Alf alarm clock", "nothing important", 19.99))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();

        Thread.sleep(1500L);

        this.webTestClient.post().uri("/test")
                .bodyValue(new Item("Smurf TV tray", "nothing important", 29.99))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();

        Thread.sleep(2000L); // <4>
/*
this.repository.findAll() // <5>
.as(StepVerifier::create) //
.expectNextMatches(item -> {
assertThat(item.getName()).isEqualTo("Alf alarm clock");
assertThat(item.getDescription()).isEqualTo("nothing important");
assertThat(item.getPrice()).isEqualTo(19.99);
return true;
}) //
.expectNextMatches(item -> {
assertThat(item.getName()).isEqualTo("Smurf TV tray");
assertThat(item.getDescription()).isEqualTo("nothing important");
assertThat(item.getPrice()).isEqualTo(29.99);
return true;
}) //
.verifyComplete();
*/
    }
    // end::spring-amqp-test[]

}