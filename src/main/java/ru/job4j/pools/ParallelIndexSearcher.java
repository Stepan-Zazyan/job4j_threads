package ru.job4j.pools;

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearcher<T> extends RecursiveTask<Integer> {

    private T[] array;
    private static int from;
    private static int to;
    private T res;

    public ParallelIndexSearcher(T[] array, int from, int to, T res) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.res = res;
    }

    public ParallelIndexSearcher() {
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return indexSearch(res, array);
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

    public Integer forkIndexSearch(T[] array, ParallelIndexSearcher<T> parallelIndexSearcher) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(parallelIndexSearcher);
          /*      new ParallelIndexSearcher<>(array, 0, array.length - 1, res);*/
    }

    private static <T> Integer indexSearch(T value, T[] array) {
        int res = -1;
        for (int i = from; i < to; i++) {
            if (Objects.equals(value, array[i])) {
                res = i;
                break;
            }
        }
        return res;
    }
}
