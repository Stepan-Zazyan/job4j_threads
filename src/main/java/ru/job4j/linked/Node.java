package ru.job4j.linked;

public class Node<T> {
    private final Node<T> next = new Node<>();
    private final T value = next.getValue();

    public Node<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }

}
