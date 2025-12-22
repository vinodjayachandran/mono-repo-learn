package com.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

/**
 * Interceptor for logging STOMP WebSocket messages.
 * Logs important events like connections, subscriptions, and message routing.
 */
@Slf4j
@Component
public class StompMessageInterceptor implements ChannelInterceptor {
    
    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        
        if (accessor != null) {
            StompCommand command = accessor.getCommand();
            
            if (command != null) {
                switch (command) {
                    case CONNECT:
                        log.info("WebSocket STOMP connection established. Session: {}", accessor.getSessionId());
                        break;
                    case CONNECTED:
                        log.debug("STOMP CONNECTED frame sent to client");
                        break;
                    case SUBSCRIBE:
                        log.info("Client subscribed to destination: {} (Subscription ID: {})", 
                                accessor.getDestination(), accessor.getSubscriptionId());
                        break;
                    case UNSUBSCRIBE:
                        log.info("Client unsubscribed. Subscription ID: {}", accessor.getSubscriptionId());
                        break;
                    case SEND:
                        log.debug("STOMP SEND frame received for destination: {}", accessor.getDestination());
                        break;
                    case MESSAGE:
                        log.debug("STOMP MESSAGE frame sent to destination: {}", accessor.getDestination());
                        break;
                    case DISCONNECT:
                        log.info("WebSocket STOMP connection closed. Session: {}", accessor.getSessionId());
                        break;
                    default:
                        log.debug("STOMP command received: {}", command);
                }
            } else {
                log.warn("Malformed STOMP frame received. Session: {}", accessor.getSessionId());
            }
        }
        
        return message;
    }
}

