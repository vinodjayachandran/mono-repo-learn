package com.example.config;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class StompMessageInterceptorTest {

    @ParameterizedTest
    @EnumSource(value = StompCommand.class, names = {
        "CONNECT",
        "CONNECTED",
        "SUBSCRIBE",
        "UNSUBSCRIBE",
        "SEND",
        "MESSAGE",
        "DISCONNECT",
        "ERROR"
    })
    void preSendHandlesKnownCommands(StompCommand command) {
        StompMessageInterceptor interceptor = new StompMessageInterceptor();
        MessageChannel channel = mock(MessageChannel.class);

        StompHeaderAccessor accessor = StompHeaderAccessor.create(command);
        accessor.setSessionId("session-1");
        accessor.setDestination("/topic/time");
        accessor.setSubscriptionId("sub-1");
        Message<byte[]> message = MessageBuilder.createMessage(new byte[0], accessor.getMessageHeaders());

        Message<?> result = interceptor.preSend(message, channel);

        assertThat(result).isSameAs(message);
    }

    @Test
    void preSendHandlesMissingAccessor() {
        StompMessageInterceptor interceptor = new StompMessageInterceptor();
        MessageChannel channel = mock(MessageChannel.class);
        Message<String> message = MessageBuilder.withPayload("payload").build();

        Message<?> result = interceptor.preSend(message, channel);

        assertThat(result).isSameAs(message);
    }
}
