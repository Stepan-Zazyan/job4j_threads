package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearcher<T> extends RecursiveTask<Integer> {

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
    protected Integer compute() {
        if (to - from <= 10) {
            return IndexSearch.indexSearch(res, array);
        }
        int mid = (from + to) / 2;
        ParallelIndexSearcher<T> leftIndex = new ParallelIndexSearcher<>(array, from, mid, res);
        ParallelIndexSearcher<T> rightIndex = new ParallelIndexSearcher<>(array, mid + 1, to, res);
        leftIndex.fork();
        rightIndex.fork();
        Integer leftJoin = leftIndex.join();
        Integer rightJoin = rightIndex.join();
        return Math.max(leftJoin, rightJoin);
    }

    public Integer forkIndexSearch(T[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexSearcher<>(array, 0, array.length - 1, res));
    }
}
