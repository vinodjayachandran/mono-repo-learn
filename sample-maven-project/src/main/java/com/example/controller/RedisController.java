package com.example.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {

    private final StringRedisTemplate redisTemplate;

    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Spring injects and manages the mutable bean safely.")
    public RedisController(final StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/add")
    public String add(@RequestParam String key, @RequestParam String value) {
        redisTemplate.opsForValue().set(key, value);
        return "Key added";
    }

    @PutMapping("/update")
    public String update(@RequestParam String key, @RequestParam String value) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.opsForValue().set(key, value);
            return "Key updated";
        }
        return "Key not found";
    }

    @GetMapping("/get")
    public String get(@RequestParam String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
