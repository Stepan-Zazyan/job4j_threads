package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    private int lockTop;

    public SimpleBlockingQueue(int lockTop) {
        this.lockTop = lockTop;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= lockTop) {
            queue.wait();
        }
        queue.offer(value);
        this.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        T value;
        while (queue.isEmpty()) {
            queue.wait();
        }
        value = queue.poll();
        lockTop++;
        this.notifyAll();
        return value;
    }

    public Queue<T> getQueue() {
        return queue;
    }
}
