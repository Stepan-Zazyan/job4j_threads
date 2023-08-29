package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParallelIndexSearcherTest {
    @Test
    void searchLinear() {
        Integer[] array = {1, 4, 6, 23, 5, 8};
        assertEquals(ParallelIndexSearcher.forkIndexSearch(array, 23), 3);
    }

    @Test
    void searchParallel() {
        Integer[] array = {1, 4, 6, 23, 5, 8, 4, 4, 5, 0, 6, 7, 8, 45, 3, 2, 4, 9};
        assertEquals(ParallelIndexSearcher.forkIndexSearch(array, 45), 13);
    }

    @Test
    void checkFalseSearch() {
        Integer[] array = {1, 4, 6, 23, 5, 8, 4, 4, 5, 0, 6, 7, 8, 45, 3, 2, 4, 9};
        assertEquals(ParallelIndexSearcher.forkIndexSearch(array, 645), -1);
    }

    @Test
    void checkStringElement() {
        String[] strArray = {"1", "4", "6", "23", "5", "8", "4", "4", "5", "0", "6", "7", "8", "45"};
        assertEquals(ParallelIndexSearcher.forkIndexSearch(strArray, "6"), 10);
    }
}