package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class RolColSumTest {

    @Test
    void linearCount() {
        int[][] matrix = {{1, 2, 3},
                {2, 2, 2},
                {2, 3, 2}};
        Sums[] result = RolColSum.linearSum(matrix);
        Sums[] expected = {
                new Sums(6, 5),
                new Sums(6, 7),
                new Sums(7, 7),
        };
        assertArrayEquals(result, expected);
    }

    @Test
    void asyncCount() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3},
                {2, 2, 2},
                {2, 3, 2}};
        Sums[] result = RolColSum.asyncSum(matrix);
        Sums[] expected = {
                new Sums(6, 5),
                new Sums(6, 7),
                new Sums(7, 7),
        };
        assertArrayEquals(result, expected);
    }
}