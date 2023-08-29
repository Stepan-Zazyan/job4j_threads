package ru.job4j.pools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            futures.put(i, getTask(matrix, i));
        }
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    public static CompletableFuture<Sums> getTask(int[][] matrix, int rowCol) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sum = new Sums();
            for (int j = 0; j < matrix[0].length; j++) {
                sum.setRowSum(sum.getRowSum() + matrix[rowCol][j]);
                sum.setColSum(sum.getColSum() + matrix[j][rowCol]);
            }
            return sum;
        });
    }

    public static Sums[] linearSum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            Sums sum = new Sums();
            for (int j = 0; j < matrix[0].length; j++) {
                sum.setRowSum(sum.getRowSum() + matrix[i][j]);
                sum.setColSum(sum.getColSum() + matrix[j][i]);
            }
            sums[i] = sum;
        }
        return sums;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {2, 2, 2}, {2, 3, 2}};

        double startLinearCount = System.currentTimeMillis();
        linearSum(matrix);
        double endLinearCount = System.currentTimeMillis() - startLinearCount;
        System.out.println("LinearCountTime= " + endLinearCount);
        System.out.println(Arrays.toString(linearSum(matrix)));

        double startAsyncCount = System.currentTimeMillis();
        asyncSum(matrix);
        double endAsyncCount = System.currentTimeMillis() - startAsyncCount;
        System.out.println("AsyncCountTime= " + endAsyncCount);
    }
}