package ru.job4j.pools;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearcher<T> extends RecursiveTask<int[]> {

    private final T[] array;
    private final int from;
    private final int to;
    private final T res;

    public ParallelIndexSearcher(T[] array, int from, int to, T res) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.res = res;
    }

    @Override
    protected int[] compute() {
        if (from == to) {
            return new int[]{from};
        }
        int mid = (from + to) / 2;
        // создаем задачи для сортировки частей
        ParallelIndexSearcher leftSort = new ParallelIndexSearcher(array, from, mid, res);
        ParallelIndexSearcher rightSort = new ParallelIndexSearcher(array, mid + 1, to, res);
        // производим деление.
        // оно будет происходить, пока в частях не останется по одному элементу
        leftSort.fork();
        rightSort.fork();
        // объединяем полученные результаты
        return new IndexSearch().indexSearch(res, array);
    }

    public T[] forkIndexSearch(T[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return (T[])forkJoinPool.invoke(new ParallelIndexSearcher(array, 0, array.length - 1, 5));
    }

    public static void main(String[] args) {
        Object[] array = {1, 4, 6, 23, 5, 8, 4, 4, 5, 0};
        System.out.println(Arrays.toString(array));
        Object[] a = new ParallelIndexSearcher(array, 0, array.length - 1, 5).forkIndexSearch(array);
        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(a));
        System.out.println();
    }
}
