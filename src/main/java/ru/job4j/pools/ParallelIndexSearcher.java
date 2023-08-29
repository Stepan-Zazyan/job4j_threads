package ru.job4j.pools;

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearcher<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final int from;
    private final int to;
    private final T obj;

    public ParallelIndexSearcher(T[] array, int from, int to, T obj) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.obj = obj;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return indexSearch();
        }
        int mid = (from + to) / 2;
        ParallelIndexSearcher<T> leftIndex = new ParallelIndexSearcher<>(array, from, mid, obj);
        ParallelIndexSearcher<T> rightIndex = new ParallelIndexSearcher<>(array, mid + 1, to, obj);
        leftIndex.fork();
        rightIndex.fork();
        Integer leftJoin = leftIndex.join();
        Integer rightJoin = rightIndex.join();
        return Math.max(leftJoin, rightJoin);
    }

    public static <T> Integer forkIndexSearch(T[] array, T value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexSearcher<>(array, 0, array.length - 1, value));
    }

    private Integer indexSearch() {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (Objects.equals(obj, array[i])) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }
}
