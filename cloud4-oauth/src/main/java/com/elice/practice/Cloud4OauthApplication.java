package com.elice.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Cloud4OauthApplication {
    public static void main(String[] args) {
        SpringApplication.run(Cloud4OauthApplication.class, args);
    }
}

