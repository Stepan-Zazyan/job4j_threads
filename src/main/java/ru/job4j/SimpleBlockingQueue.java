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

    public SimpleBlockingQueue() {
    }

    public SimpleBlockingQueue(int lockTop) {
        this.lockTop = lockTop;
    }

/*    public SimpleBlockingQueue() {
        this.lockTop = 100;
    }*/

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= lockTop) {
            queue.wait();
        }
        queue.offer(value);
        this.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            queue.wait();
        }
        T value = queue.poll();
        this.notifyAll();
        return value;
    }

    public Queue<T> getQueue() {
        return queue;
    }
}
