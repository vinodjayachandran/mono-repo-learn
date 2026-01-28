package com.example.service;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PingServiceTest {

    @Test
    void getPingResponseReturnsExpectedPayload() {
        PingService service = new PingService();

        Map<String, Object> response = service.getPingResponse().join();

        assertThat(response.get("status")).isEqualTo("ok");
        assertThat(response.get("message")).isEqualTo("pong");
        assertThat(response.get("timestamp")).isNotNull();
    }
}
