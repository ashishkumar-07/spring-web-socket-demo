package com.demo.websocket.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class WebSocketClient {
    public static void main(String[] args) {
        SpringApplication.run(WebSocketClient.class, args);
    }
    
}


