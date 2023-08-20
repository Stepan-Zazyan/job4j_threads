package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private static final int SIZE = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(SIZE);


    public ThreadPool() {
        for (int i = 0; i < SIZE; i++) {
            Thread thread = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        tasks.poll().run();
                        System.out.println(Thread.currentThread().getName() + " Зашла в конструктор и выполнила работу из tasks");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(Thread.currentThread().getName() + " Проскочила эксепшн");
            });
            thread.start();
            threads.add(thread);
        }
    }

    public synchronized void work(Runnable job) throws InterruptedException {
            tasks.offer(job);
    }

    public synchronized void shutdown() {
        for (Thread x : threads) {
            System.out.println(x.getName() + " В состоянии интераптед");
            x.interrupt();
        }
    }


    public SimpleBlockingQueue<Runnable> getTasks() {
        return tasks;
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 4; i < 10; i++) {
            threadPool.threads.add(new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        threadPool.getTasks().poll().run();
                        System.out.println(Thread.currentThread().getName() + " Зашла в конструктор и выполнила работу из tasks");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(Thread.currentThread().getName() + " Проскочила эксепшн");
            }));
            threadPool.threads.get(i).start();
        }
        threadPool.work(() -> System.out.println(Thread.currentThread().getName() + " Загрузила работу в tasks"));
        threadPool.shutdown();
    }

}


