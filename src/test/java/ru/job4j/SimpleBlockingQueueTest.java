package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void commonT() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(10);
        int[] list = new int[3];
        Thread producer = new Thread(
                () -> {
                    try {
                        sbq.offer(1);
                        sbq.offer(2);
                        sbq.offer(3);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    try {
                        list[0] = (sbq.poll());
                        list[1] = (sbq.poll());
                        list[2] = (sbq.poll());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(list).isEqualTo(new int[]{1, 2, 3});
        assertThat(list[1]).isEqualTo(2);
    }
    @Test
    void checkInALoop() throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(10);
            int[] list = new int[3];
            Thread producer = new Thread(
                    () -> {
                        try {
                            sbq.offer(1);
                            sbq.offer(2);
                            sbq.offer(3);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
            Thread consumer = new Thread(
                    () -> {
                        try {
                            list[0] = (sbq.poll());
                            list[1] = (sbq.poll());
                            list[2] = (sbq.poll());
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
            producer.start();
            producer.join();
            consumer.start();
            consumer.join();
            assertThat(list[1]).isEqualTo(2);
        }
    }
}