package ru.job4j.cache;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) throws Exception {
        Base oldModel = memory.get(model.getId());
        int version = model.getVersion();
        Base newModel = new Base(model.getId(), version + 1);
        boolean rsl = memory.replace(model.getId(),
                oldModel,
                newModel);
        BiFunction<Integer, Integer, Boolean> func = (s, t) -> !Objects.equals(s, t);
        if (func.apply(oldModel.getVersion(), model.getVersion())) {
            throw new Exception("Апдейт не удался! P.S. Проект не находит OptimisticExeption");
        }
        return rsl;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Map<Integer, Base> getMemory() {
        return memory;
    }
}
