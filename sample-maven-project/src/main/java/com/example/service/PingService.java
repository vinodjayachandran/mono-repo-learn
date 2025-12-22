package com.example.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class PingService {
    
    @Async
    public CompletableFuture<Map<String, Object>> getPingResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ok");
        response.put("message", "pong");
        response.put("timestamp", LocalDateTime.now().toString());
        return CompletableFuture.completedFuture(response);
    }
}

