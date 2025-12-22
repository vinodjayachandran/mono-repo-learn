package com.example.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuration for WebSocket and STOMP message broker.
 * Enables WebSocket support with STOMP protocol for real-time messaging.
 */
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    private final StompMessageInterceptor stompMessageInterceptor;
    
    @Override
    public void configureMessageBroker(@NonNull MessageBrokerRegistry config) {
        // Enable simple in-memory message broker for broadcasting to /topic destinations
        config.enableSimpleBroker("/topic");
        // Set prefix for application destinations (messages sent from client to server)
        config.setApplicationDestinationPrefixes("/app");
    }
    
    @Override
    public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {
        // Register STOMP endpoint with SockJS fallback for browser compatibility
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
        
        // Register raw WebSocket endpoint for non-browser clients
        registry.addEndpoint("/ws-raw")
                .setAllowedOriginPatterns("*");
    }
    
    @Override
    public void configureClientInboundChannel(@NonNull ChannelRegistration registration) {
        // Register interceptor for logging incoming STOMP messages
        registration.interceptors(stompMessageInterceptor);
    }
    
    @Override
    public void configureClientOutboundChannel(@NonNull ChannelRegistration registration) {
        // Register interceptor for logging outgoing STOMP messages
        registration.interceptors(stompMessageInterceptor);
    }
}

