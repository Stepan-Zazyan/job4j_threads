package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);
    int size = Runtime.getRuntime().availableProcessors();

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                try {
                    synchronized (this) {
                        wait();
                    }
                    while (!tasks.getQueue().isEmpty()
                                && !Thread.currentThread().isInterrupted()) {
                            tasks.poll().run();
                            System.out.println(Thread.currentThread().getName() + " Зашла в конструктор и выполнила работу из tasks");
                        }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " Проскочила эксепшн");
            });
            thread.start();
            threads.add(thread);
        }
    }

    public synchronized void work(Runnable job) throws InterruptedException {
        for (int i = 0; i < size; i++) {
            tasks.offer(job);
            System.out.println(Thread.currentThread().getName() + " отработал метод work");
        }
        notifyAll();
    }

    public synchronized void shutdown() {
        for (Thread x : threads) {
            System.out.println(x.getName() + " В состоянии интераптед");
            x.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        threadPool.work(() -> System.out.println(Thread.currentThread().getName() + " Загрузила работу в tasks"));
        threadPool.shutdown();

    }
}

