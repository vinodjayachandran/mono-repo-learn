package com.example;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    @Test
    void tryParseLongReturnsValue() {
        Optional<Long> result = App.tryParseLong("42");
        assertThat(result).contains(42L);
    }

    @Test
    void tryParseLongReturnsEmptyOnInvalidInput() {
        Optional<Long> result = App.tryParseLong("not-a-number");
        assertThat(result).isEmpty();
    }
}
