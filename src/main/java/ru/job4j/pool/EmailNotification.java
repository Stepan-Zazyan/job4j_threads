package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    void emailTo(User user) {
        pool.submit(
                () -> {
                    String subject = String.format("subject = Notification %s to email %s", user.getUsername(), user.getEmail());
                    String body = String.format("body = Add a new event to %s", user.getUsername());
                    send(subject, body, user.getEmail());
                }
        );
    }

    void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void send(String subject, String body, String email) {

    }
}

