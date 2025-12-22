package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Service for retrieving current time with asynchronous processing.
 */
@Slf4j
@Service
public class TimeService {
    
    private static final int DELAY_SECONDS = 3;
    
    /**
     * Retrieves the current time asynchronously after a 3-second delay.
     * This demonstrates asynchronous processing in the application.
     *
     * @return CompletableFuture containing the time response
     */
    @Async
    public CompletableFuture<Map<String, Object>> getCurrentTime() {
        log.debug("Processing time request asynchronously");
        
        try {
            Thread.sleep(DELAY_SECONDS * 1000L);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "ok");
            response.put("currentTime", LocalDateTime.now().toString());
            response.put("message", "Time retrieved after 3 seconds delay");
            
            log.debug("Time response created successfully");
            return CompletableFuture.completedFuture(response);
        } catch (InterruptedException e) {
            log.error("Time service interrupted", e);
            Thread.currentThread().interrupt();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Interrupted while waiting");
            return CompletableFuture.completedFuture(errorResponse);
        }
    }
}

