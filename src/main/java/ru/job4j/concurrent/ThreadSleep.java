package ru.job4j.concurrent;

public class ThreadSleep {
    public static void main(String[] args) {
        Thread th = new Thread(
               () -> {
                   try {
                      for (int i = 0; i < 100; i++) {
                          System.out.print("\rLoading..." + i + "%");
                          Thread.sleep(1000);
                      }
                          System.out.println("Loaded");

                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
        );
        th.start();
    }
}
