package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

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
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(
                () -> IntStream.range(0, 7).forEach(
                        queue.getQueue()::offer
                )
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.getQueue().isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.join();
        consumer.start();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(0, 1, 2, 3, 4, 5, 6);
    }
}