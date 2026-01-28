package com.example.controller;

import com.example.service.TimeService;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TimeControllerTest {

    @Test
    void getCurrentTimeDelegatesToService() {
        TimeService service = mock(TimeService.class);
        Map<String, Object> payload = Map.of("status", "ok");
        CompletableFuture<Map<String, Object>> future = CompletableFuture.completedFuture(payload);
        when(service.getCurrentTime()).thenReturn(future);

        TimeController controller = new TimeController(service);

        assertThat(controller.getCurrentTime().join()).isEqualTo(payload);
    }
}
