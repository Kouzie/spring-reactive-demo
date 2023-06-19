package com.example.react.r2dbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class ReactSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReactSecurityApplication.class, args);
    }
}
