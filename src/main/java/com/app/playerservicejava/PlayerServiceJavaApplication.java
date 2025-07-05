package com.app.playerservicejava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class PlayerServiceJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlayerServiceJavaApplication.class, args);
    }

}
