package ru.job4j.pools;

import java.util.Objects;

public class IndexSearch {

    public static <T> Integer indexSearch(T value, T[] array) {
        int res = -1;
        for (int i = 0; i < array.length; i++) {
            if (Objects.equals(value, array[i])) {
                res = i;
                break;
            }
        }
        return res;
    }

}
