package com.example.config;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;
import org.springframework.web.socket.config.annotation.SockJsServiceRegistration;

import static org.mockito.Mockito.*;

class WebSocketConfigTest {

    @Test
    void configureMessageBrokerSetsPrefixes() {
        StompMessageInterceptor interceptor = mock(StompMessageInterceptor.class);
        WebSocketConfig config = new WebSocketConfig(interceptor);
        MessageBrokerRegistry registry = mock(MessageBrokerRegistry.class);

        config.configureMessageBroker(registry);

        verify(registry).enableSimpleBroker("/topic");
        verify(registry).setApplicationDestinationPrefixes("/app");
    }

    @Test
    void registerStompEndpointsRegistersBothEndpoints() {
        StompMessageInterceptor interceptor = mock(StompMessageInterceptor.class);
        WebSocketConfig config = new WebSocketConfig(interceptor);
        StompEndpointRegistry registry = mock(StompEndpointRegistry.class);

        StompWebSocketEndpointRegistration registrationWs = mock(StompWebSocketEndpointRegistration.class);
        StompWebSocketEndpointRegistration registrationRaw = mock(StompWebSocketEndpointRegistration.class);
        when(registry.addEndpoint("/ws")).thenReturn(registrationWs);
        when(registry.addEndpoint("/ws-raw")).thenReturn(registrationRaw);
        when(registrationWs.setAllowedOriginPatterns("*")).thenReturn(registrationWs);
        when(registrationRaw.setAllowedOriginPatterns("*")).thenReturn(registrationRaw);
        when(registrationWs.withSockJS()).thenReturn(mock(SockJsServiceRegistration.class));

        config.registerStompEndpoints(registry);

        verify(registry).addEndpoint("/ws");
        verify(registry).addEndpoint("/ws-raw");
        verify(registrationWs).setAllowedOriginPatterns("*");
        verify(registrationRaw).setAllowedOriginPatterns("*");
        verify(registrationWs).withSockJS();
    }

    @Test
    void configureClientChannelsRegistersInterceptor() {
        StompMessageInterceptor interceptor = mock(StompMessageInterceptor.class);
        WebSocketConfig config = new WebSocketConfig(interceptor);
        ChannelRegistration inbound = mock(ChannelRegistration.class);
        ChannelRegistration outbound = mock(ChannelRegistration.class);

        config.configureClientInboundChannel(inbound);
        config.configureClientOutboundChannel(outbound);

        verify(inbound).interceptors(interceptor);
        verify(outbound).interceptors(interceptor);
    }
}
