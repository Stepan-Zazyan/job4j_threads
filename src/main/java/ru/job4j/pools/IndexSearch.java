package ru.job4j.pools;

import java.util.ArrayList;
import java.util.List;

public class IndexSearch<T> {
    public int[] indexSearch(T value, T[] array) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                list.add(i);
            }
        }
        if (list.isEmpty()) {
            throw new IllegalArgumentException("В массиве нет такого объекта");
        }
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }
}
