package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {
                }
        );
        Thread second = new Thread(
                () -> {
                }
        );
        System.out.println(first.getName());
        System.out.println(second.getName());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED
        || second.getState() != Thread.State.TERMINATED) {
            System.out.println("активна нить " + Thread.currentThread().getName());
        }
        System.out.println("Работа нитей завершена " + System.lineSeparator()
                + first.getName() + " " + first.getState() + System.lineSeparator()
                + second.getName() + " " + second.getState());
    }
}