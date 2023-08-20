package ru.job4j.pools;

public class IndexSearch<T> {
    public Integer indexSearch(T value, T[] array) {
        int res = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                res = i;
            }
        }
        if (res == -1) {
            throw new IllegalArgumentException("В массиве нет такого объекта");
        }
        return res;
    }
}
