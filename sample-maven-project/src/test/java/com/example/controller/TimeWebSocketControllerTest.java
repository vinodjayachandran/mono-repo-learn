package com.example.controller;

import com.example.service.TimeService;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TimeWebSocketControllerTest {

    @Test
    void handleTimeRequestReturnsServiceFuture() {
        TimeService service = mock(TimeService.class);
        CompletableFuture<Map<String, Object>> future = CompletableFuture.completedFuture(Map.of("status", "ok"));
        when(service.getCurrentTime()).thenReturn(future);

        TimeWebSocketController controller = new TimeWebSocketController(service);

        assertThat(controller.handleTimeRequest(Map.of())).isSameAs(future);
    }

    @Test
    void handleTimeRequestPropagatesFailures() {
        TimeService service = mock(TimeService.class);
        CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();
        future.completeExceptionally(new RuntimeException("boom"));
        when(service.getCurrentTime()).thenReturn(future);

        TimeWebSocketController controller = new TimeWebSocketController(service);

        assertThat(controller.handleTimeRequest(Map.of())).isSameAs(future);
        assertThat(future).isCompletedExceptionally();
    }
}
