package com.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

/**
 * Event listener for WebSocket lifecycle events.
 * Provides logging for connection, disconnection, and subscription events.
 */
@Slf4j
@Component
public class WebSocketEventListener {
    
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        try {
            StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
            log.info("WebSocket session connected. Session ID: {}", headerAccessor.getSessionId());
        } catch (Exception e) {
            log.error("Error processing WebSocket connection event", e);
        }
    }
    
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        try {
            StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
            log.info("WebSocket session disconnected. Session ID: {}", headerAccessor.getSessionId());
        } catch (Exception e) {
            log.error("Error processing WebSocket disconnection event", e);
        }
    }
    
    @EventListener
    public void handleSubscribeEvent(SessionSubscribeEvent event) {
        try {
            StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
            log.debug("Client subscribed. Session: {}, Destination: {}, Subscription ID: {}", 
                    headerAccessor.getSessionId(), 
                    headerAccessor.getDestination(), 
                    headerAccessor.getSubscriptionId());
        } catch (Exception e) {
            log.error("Error processing subscription event", e);
        }
    }
    
    @EventListener
    public void handleUnsubscribeEvent(SessionUnsubscribeEvent event) {
        try {
            StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
            log.debug("Client unsubscribed. Session: {}, Subscription ID: {}", 
                    headerAccessor.getSessionId(), 
                    headerAccessor.getSubscriptionId());
        } catch (Exception e) {
            log.error("Error processing unsubscription event", e);
        }
    }
}

