package com.my.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class MyCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyCacheApplication.class, args);
    }

}
