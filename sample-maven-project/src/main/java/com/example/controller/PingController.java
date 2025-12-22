package com.example.controller;

import com.example.service.PingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * REST controller for health check endpoints.
 */
@RestController
@RequiredArgsConstructor
public class PingController {
    
    private final PingService pingService;
    
    /**
     * Health check endpoint that returns a ping response.
     *
     * @return CompletableFuture containing the ping response with status, message, and timestamp
     */
    @GetMapping("/ping")
    public CompletableFuture<Map<String, Object>> ping() {
        return pingService.getPingResponse();
    }
}

