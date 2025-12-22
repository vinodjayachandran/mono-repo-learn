package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for Spring Boot App.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppTest {
    
    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void testPingEndpoint() {
        String url = "http://localhost:" + port + "/ping";
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ok", response.getBody().get("status"));
        assertEquals("pong", response.getBody().get("message"));
        assertTrue(response.getBody().containsKey("timestamp"));
    }
    
    @Test
    public void testTryParseLong() {
        assertTrue(App.tryParseLong("999").isPresent());
        assertEquals(999L, App.tryParseLong("999").get());
        assertFalse(App.tryParseLong("invalid").isPresent());
    }
}
