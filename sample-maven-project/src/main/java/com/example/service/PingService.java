package com.example.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Service for handling ping requests.
 * Provides health check functionality with asynchronous processing.
 */
@Service
public class PingService {
    
    /**
     * Generates a ping response asynchronously.
     *
     * @return CompletableFuture containing the ping response with status, message, and timestamp
     */
    @Async
    public CompletableFuture<Map<String, Object>> getPingResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ok");
        response.put("message", "pong");
        response.put("timestamp", LocalDateTime.now().toString());
        return CompletableFuture.completedFuture(response);
    }
}

