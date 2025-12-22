package com.example.controller;

import com.example.service.TimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * WebSocket controller for handling time requests via STOMP protocol.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class TimeWebSocketController {
    
    private final TimeService timeService;
    
    /**
     * Handles WebSocket messages sent to /app/time.
     * Processes the request asynchronously and sends response to /topic/time after 3 seconds.
     *
     * @param message the incoming message (can be empty)
     * @return CompletableFuture containing the time response
     */
    @MessageMapping("/time")
    @SendTo("/topic/time")
    public CompletableFuture<Map<String, Object>> handleTimeRequest(Map<String, String> message) {
        log.debug("Received time request via WebSocket");
        
        CompletableFuture<Map<String, Object>> future = timeService.getCurrentTime();
        
        future.thenAccept(result -> {
            log.debug("Time response ready, sending to /topic/time");
        }).exceptionally(throwable -> {
            log.error("Error processing time request", throwable);
            return null;
        });
        
        return future;
    }
}

