package com.gapco.backend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GapcoApp {
    public static void main(String[] args) {
        SpringApplication.run(GapcoApp.class, args);
    }
}
