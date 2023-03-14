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
        first.start();
        while (first.getState() != Thread.State.TERMINATED) {
            System.out.println("активна нить " + first.getName() + " " + first.getState());
        }
        System.out.println(second.getName());
        second.start();
        while (second.getState() != Thread.State.TERMINATED) {
            System.out.println("активна нить " + second.getName() + " " + second.getState());
        }
        System.out.println();
        System.out.println("Работа нитей завершена " + System.lineSeparator()
                + first.getName() + " " + first.getState() + System.lineSeparator()
                + second.getName() + " " + second.getState());
    }
}