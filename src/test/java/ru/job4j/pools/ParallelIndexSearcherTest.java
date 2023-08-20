package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ParallelIndexSearcherTest {

    @Test
    void searchLinear() {
        Integer[] array = {1, 4, 6, 23, 5, 8};
        Integer a =
                new ParallelIndexSearcher<>(array, 0, array.length - 1, 23)
                        .forkIndexSearch(array);
        assertEquals(a, 3);
    }

    @Test
    void searchParallel() {
        Integer[] array = {1, 4, 6, 23, 5, 8, 4, 4, 5, 0, 6, 7, 8, 45, 3, 2, 4, 9};
        Integer a =
                new ParallelIndexSearcher<>(array, 0, array.length - 1, 45)
                        .forkIndexSearch(array);
        assertEquals(a, 13);
    }

    @Test
    void searchSameNumbersIsLastIndex() {
        Integer[] array = {1, 4, 6, 23, 5, 8, 4, 4, 5, 0, 6, 7, 8, 45, 3, 2, 4, 9};
        Integer a =
                new ParallelIndexSearcher<>(array, 0, array.length - 1, 4)
                        .forkIndexSearch(array);
        assertEquals(a, 16);
    }

    @Test
    void noSuchElementFound() {
        Integer[] array = {1, 4, 6, 23, 5, 8, 4, 4, 5, 0, 6, 7, 8, 45, 3, 2, 4, 9};
        assertThatThrownBy(() -> new ParallelIndexSearcher<>(array, 0, array.length - 1, 111)
                .forkIndexSearch(array))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("В массиве нет такого объекта");
    }

    @Test
    void checkStringElement() {
        String[] strArray = {"1", "4", "6", "23", "5", "8", "4", "4", "5", "0", "6", "7", "8", "45"};
        Integer b =
                new ParallelIndexSearcher<>(strArray, 0, strArray.length - 1, "45")
                        .forkIndexSearch(strArray);
        assertEquals(b, 13);
    }
}