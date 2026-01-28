package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiIntegrationTest {

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:alpine")
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testPing() {
        ResponseEntity<Map> response = restTemplate.getForEntity("/ping", Map.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody()).containsKey("status");
    }

    @Test
    void testTime() {
        ResponseEntity<Map> response = restTemplate.getForEntity("/time", Map.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody()).containsKey("currentTime"); // Assuming keys based on typical implementations,
                                                                   // might need adjustment if key is different
    }

    @Test
    void testRedisOperations() {
        // Add
        ResponseEntity<String> addResponse = restTemplate.postForEntity("/redis/add?key=testKey&value=testValue", null,
                String.class);
        assertEquals(HttpStatus.OK, addResponse.getStatusCode());
        assertEquals("Key added", addResponse.getBody());

        // Get
        ResponseEntity<String> getResponse = restTemplate.getForEntity("/redis/get?key=testKey", String.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals("testValue", getResponse.getBody());

        // Update
        ResponseEntity<String> updateResponse = restTemplate.exchange("/redis/update?key=testKey&value=newValue",
                HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertEquals("Key updated", updateResponse.getBody());

        // Get verified
        String updatedValue = restTemplate.getForObject("/redis/get?key=testKey", String.class);
        assertEquals("newValue", updatedValue);
    }
}
