package com.example;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class RedisE2ETest {

    @BeforeAll
    static void configureBaseUrl() {
        String baseUrl = System.getProperty("e2e.baseUrl");
        assumeTrue(baseUrl != null && !baseUrl.isBlank(), "e2e.baseUrl not set");
        RestAssured.baseURI = baseUrl;
    }

    @Test
    void actuatorHealthIsOk() {
        given()
            .when()
            .get("/actuator/health")
        .then()
            .statusCode(200);
    }

    @Test
    void actuatorInfoIsOk() {
        given()
            .when()
            .get("/actuator/info")
        .then()
            .statusCode(200);
    }

    @Test
    void pingIsOk() {
        given()
            .when()
            .get("/ping")
        .then()
            .statusCode(200);
    }

    @Test
    void redisAddGetUpdateFlow() {
        String key = "ci-key-" + UUID.randomUUID();

        given()
            .queryParam("key", key)
            .queryParam("value", "one")
        .when()
            .post("/redis/add")
        .then()
            .statusCode(200)
            .body(equalTo("Key added"));

        given()
            .queryParam("key", key)
        .when()
            .get("/redis/get")
        .then()
            .statusCode(200)
            .body(equalTo("one"));

        given()
            .queryParam("key", key)
            .queryParam("value", "two")
        .when()
            .put("/redis/update")
        .then()
            .statusCode(200)
            .body(equalTo("Key updated"));

        given()
            .queryParam("key", key)
        .when()
            .get("/redis/get")
        .then()
            .statusCode(200)
            .body(equalTo("two"));
    }
}
