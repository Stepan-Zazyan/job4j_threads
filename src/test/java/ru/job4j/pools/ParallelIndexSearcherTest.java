package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ParallelIndexSearcherTest {

    @Test
    void searchLinear() {
        Integer[] array = {1, 4, 6, 23, 5, 8};
        ParallelIndexSearcher<Object> parallelIndexSearcher =
                new ParallelIndexSearcher<>(array, 0, array.length - 1, 23);
        Integer a =
                new ParallelIndexSearcher<>()
                        .forkIndexSearch(array, parallelIndexSearcher);
        assertEquals(a, 3);
    }

    @Test
    void searchParallel() {
        Integer[] array = {1, 4, 6, 23, 5, 8, 4, 4, 5, 0, 6, 7, 8, 45, 3, 2, 4, 9};
        ParallelIndexSearcher<Object> parallelIndexSearcher =
                new ParallelIndexSearcher<>(array, 0, array.length - 1, 45);
        Integer a = new ParallelIndexSearcher<>()
                        .forkIndexSearch(array, parallelIndexSearcher);
        assertEquals(13, a);
    }

    @Test
    void checkStringElement() {
        String[] strArray = {"1", "4", "6", "23", "5", "8", "4", "4", "5", "0", "6", "7", "8", "45"};
        ParallelIndexSearcher<Object> parallelIndexSearcher =
                new ParallelIndexSearcher<>(strArray, 0, strArray.length - 1, "45");
        Integer b = new ParallelIndexSearcher<>()
                        .forkIndexSearch(strArray, parallelIndexSearcher);
        assertEquals(13, b);
    }
}