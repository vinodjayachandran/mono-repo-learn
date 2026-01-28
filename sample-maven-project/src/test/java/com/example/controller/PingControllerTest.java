package com.example.controller;

import com.example.service.PingService;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PingControllerTest {

    @Test
    void pingDelegatesToService() {
        PingService service = mock(PingService.class);
        Map<String, Object> payload = Map.of("status", "ok");
        CompletableFuture<Map<String, Object>> future = CompletableFuture.completedFuture(payload);
        when(service.getPingResponse()).thenReturn(future);

        PingController controller = new PingController(service);

        assertThat(controller.ping().join()).isEqualTo(payload);
    }
}
