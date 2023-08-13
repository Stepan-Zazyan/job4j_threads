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
                    while (tasks.getQueue().isEmpty()) {
                        synchronized (this) {
                            wait();
                        }
                    }
                    System.out.println(tasks.poll() + Thread.currentThread().getName() + "отработала в конструкторе");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            threads.add(thread);
        }
    }

    public synchronized void work(Runnable job) throws InterruptedException {
            tasks.offer(job);
            notifyAll();
            System.out.println(Thread.currentThread().getName() + " Отработала в методе Work");
    }

    public void shutdown() {
        for (Thread x : threads) {
            System.out.println("Thread Interrupted " + Thread.currentThread().getName());
            x.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        Thread.sleep(1000);
        for (Thread x : threadPool.threads) {
            x.start();
        }
        threadPool.work(() -> System.out.println("Thread did tasks.offer in method Work" + Thread.currentThread().getName()));
        threadPool.shutdown();
        System.out.println(threadPool.threads);
    }
}
