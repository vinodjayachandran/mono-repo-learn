package com.example.controller;

import com.example.service.PingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
public class PingController {
    
    private final PingService pingService;
    
    @Autowired
    public PingController(PingService pingService) {
        this.pingService = pingService;
    }
    
    @GetMapping("/ping")
    public CompletableFuture<Map<String, Object>> ping() {
        return pingService.getPingResponse();
    }
}

