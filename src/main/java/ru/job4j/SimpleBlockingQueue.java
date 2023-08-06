package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    //добавляет
    public void offer(T value) {
        synchronized (queue) {
            queue.offer(value);
            queue.notify();
        }
    }

    //удаляет
    public T poll() throws InterruptedException {
        T value;
        synchronized (queue) {
            while (queue.isEmpty()) {
                queue.wait();
            }
            value = queue.poll();
            queue.notify();
        }
        return value;
    }
}
