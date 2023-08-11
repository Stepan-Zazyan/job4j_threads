package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(100);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                try {
                    while (tasks.getQueue().isEmpty()) {
                        Thread.currentThread().wait();
                    }
                    tasks.poll();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            this.threads.add(thread);
        }
    }

    public void work(Runnable job) throws InterruptedException {
        threads.notifyAll();
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread x : threads) {
            x.interrupt();
        }
    }
}
