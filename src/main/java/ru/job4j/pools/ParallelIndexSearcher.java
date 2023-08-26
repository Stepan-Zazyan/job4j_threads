package ru.job4j.pools;

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearcher<T> extends RecursiveTask<Integer> {

    private T[] array;
    private int from;
    private int to;
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
            return indexSearch();
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

    public static <T> Integer forkIndexSearch(T[] array, T value)  {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ForkJoinTask<>() {
            @Override
            public Integer getRawResult() {
                return null;
            }

            @Override
            protected void setRawResult(Integer value) {

            }

            @Override
            protected boolean exec() {
                return false;
            }
        });
          /*      new ParallelIndexSearcher<>(array, 0, array.length - 1, res);*/
    }

    private Integer indexSearch() {
        int rsl = -1;
        for (int i = from; i < to; i++) {
            if (Objects.equals(res, array[i])) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }
}
