package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {
    @Test
    void simpleIncrement() throws InterruptedException {
        CASCount count = new CASCount();
        Thread one = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                count.increment();
            }
        });
        Thread two = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                count.increment();
            }
        });
        one.start();
        two.start();
        one.join();
        two.join();
        assertThat(count.get()).isEqualTo(10);
    }

}