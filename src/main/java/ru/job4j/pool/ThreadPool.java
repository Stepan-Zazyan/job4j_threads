package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                try {
                    while (!tasks.getQueue().isEmpty()) {
                        tasks.poll();
                    }
                    System.out.println("tasks.poll(); " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            threads.add(thread);
        }
    }

    public void work(Runnable job) throws InterruptedException {
            tasks.offer(job);
            System.out.println("Did job - offered to tasks " + Thread.currentThread().getName());
    }

    public void shutdown() {
        for (Thread x : threads) {
            System.out.println("Thread Interrupted " + Thread.currentThread().getName());
            x.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        threadPool.work(() -> System.out.println("working here " + Thread.currentThread().getName()));
        for (Thread x : threadPool.threads) {
            x.start();
            x.join();
        }
        threadPool.shutdown();
        System.out.println(threadPool.threads);
    }
}
