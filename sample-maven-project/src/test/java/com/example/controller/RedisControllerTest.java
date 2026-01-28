package com.example.controller;

import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RedisControllerTest {

    @Test
    void addStoresValue() {
        StringRedisTemplate template = mock(StringRedisTemplate.class);
        ValueOperations<String, String> valueOps = mock(ValueOperations.class);
        when(template.opsForValue()).thenReturn(valueOps);

        RedisController controller = new RedisController(template);

        String response = controller.add("key", "value");

        assertThat(response).isEqualTo("Key added");
        verify(valueOps).set("key", "value");
    }

    @Test
    void updateUpdatesExistingKey() {
        StringRedisTemplate template = mock(StringRedisTemplate.class);
        ValueOperations<String, String> valueOps = mock(ValueOperations.class);
        when(template.opsForValue()).thenReturn(valueOps);
        when(template.hasKey("key")).thenReturn(true);

        RedisController controller = new RedisController(template);

        String response = controller.update("key", "value");

        assertThat(response).isEqualTo("Key updated");
        verify(valueOps).set("key", "value");
    }

    @Test
    void updateReturnsNotFoundWhenMissing() {
        StringRedisTemplate template = mock(StringRedisTemplate.class);
        ValueOperations<String, String> valueOps = mock(ValueOperations.class);
        when(template.opsForValue()).thenReturn(valueOps);
        when(template.hasKey("key")).thenReturn(false);

        RedisController controller = new RedisController(template);

        String response = controller.update("key", "value");

        assertThat(response).isEqualTo("Key not found");
        verify(valueOps, never()).set(anyString(), anyString());
    }

    @Test
    void getReturnsValue() {
        StringRedisTemplate template = mock(StringRedisTemplate.class);
        ValueOperations<String, String> valueOps = mock(ValueOperations.class);
        when(template.opsForValue()).thenReturn(valueOps);
        when(valueOps.get("key")).thenReturn("value");

        RedisController controller = new RedisController(template);

        assertThat(controller.get("key")).isEqualTo("value");
    }
}
