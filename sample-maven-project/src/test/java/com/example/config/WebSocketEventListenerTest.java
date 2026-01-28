package com.example.config;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

class WebSocketEventListenerTest {

    private final WebSocketEventListener listener = new WebSocketEventListener();

    @Test
    void handleWebSocketConnectListenerHandlesEvent() {
        SessionConnectedEvent event = new SessionConnectedEvent(this, buildMessage(StompCommand.CONNECT));
        listener.handleWebSocketConnectListener(event);
    }

    @Test
    void handleWebSocketDisconnectListenerHandlesEvent() {
        SessionDisconnectEvent event = new SessionDisconnectEvent(this, buildMessage(StompCommand.DISCONNECT), "session-1", null);
        listener.handleWebSocketDisconnectListener(event);
    }

    @Test
    void handleSubscribeEventHandlesEvent() {
        SessionSubscribeEvent event = new SessionSubscribeEvent(this, buildMessage(StompCommand.SUBSCRIBE));
        listener.handleSubscribeEvent(event);
    }

    @Test
    void handleUnsubscribeEventHandlesEvent() {
        SessionUnsubscribeEvent event = new SessionUnsubscribeEvent(this, buildMessage(StompCommand.UNSUBSCRIBE));
        listener.handleUnsubscribeEvent(event);
    }

    @Test
    void handleWebSocketConnectListenerHandlesErrors() {
        SessionConnectedEvent event = new SessionConnectedEvent(this, throwingMessage());
        listener.handleWebSocketConnectListener(event);
    }

    @Test
    void handleWebSocketDisconnectListenerHandlesErrors() {
        SessionDisconnectEvent event = new SessionDisconnectEvent(this, throwingMessage(), "session-1", null);
        listener.handleWebSocketDisconnectListener(event);
    }

    @Test
    void handleSubscribeEventHandlesErrors() {
        SessionSubscribeEvent event = new SessionSubscribeEvent(this, throwingMessage());
        listener.handleSubscribeEvent(event);
    }

    @Test
    void handleUnsubscribeEventHandlesErrors() {
        SessionUnsubscribeEvent event = new SessionUnsubscribeEvent(this, throwingMessage());
        listener.handleUnsubscribeEvent(event);
    }

    private Message<byte[]> buildMessage(StompCommand command) {
        StompHeaderAccessor accessor = StompHeaderAccessor.create(command);
        accessor.setSessionId("session-1");
        accessor.setDestination("/topic/time");
        accessor.setSubscriptionId("sub-1");
        return MessageBuilder.createMessage(new byte[0], accessor.getMessageHeaders());
    }

    private Message<byte[]> throwingMessage() {
        return new Message<>() {
            @Override
            public byte[] getPayload() {
                return new byte[0];
            }

            @Override
            public MessageHeaders getHeaders() {
                throw new RuntimeException("boom");
            }
        };
    }
}
