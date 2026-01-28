package com.example.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class TimeServiceTest {

    @Test
    @Timeout(5)
    void getCurrentTimeReturnsSuccessPayload() {
        TimeService service = new TimeService();

        Map<String, Object> response = service.getCurrentTime().join();

        assertThat(response.get("status")).isEqualTo("ok");
        assertThat(response.get("currentTime")).isNotNull();
        assertThat(response.get("message")).isEqualTo("Time retrieved after 3 seconds delay");
    }

    @Test
    void getCurrentTimeReturnsErrorWhenInterrupted() {
        TimeService service = new TimeService();
        Thread.currentThread().interrupt();
        try {
            Map<String, Object> response = service.getCurrentTime().join();
            assertThat(response.get("status")).isEqualTo("error");
            assertThat(response.get("message")).isEqualTo("Interrupted while waiting");
        } finally {
            Thread.interrupted();
        }
    }
}
