package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    int lockTop = 1000;

    public synchronized void offer(T value) {
        synchronized (queue) {
            while (lockTop > 0 && lockTop <= 1000) {
                queue.offer(value);
                lockTop--;
                queue.notify();
            }
        }
    }

    public synchronized T poll() throws InterruptedException {
        T value;
        synchronized (queue) {
            while (queue.isEmpty()) {
                queue.wait();
            }
            value = queue.poll();
            lockTop++;
            queue.notify();
        }
        return value;
    }
}
