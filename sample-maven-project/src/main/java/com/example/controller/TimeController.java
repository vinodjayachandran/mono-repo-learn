package com.example.controller;

import com.example.service.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * REST controller for time-related endpoints.
 */
@RestController
@RequiredArgsConstructor
public class TimeController {
    
    private final TimeService timeService;
    
    /**
     * Retrieves the current time asynchronously.
     * The response is delayed by 3 seconds to demonstrate asynchronous processing.
     *
     * @return CompletableFuture containing the current time response
     */
    @GetMapping("/time")
    public CompletableFuture<Map<String, Object>> getCurrentTime() {
        return timeService.getCurrentTime();
    }
}

