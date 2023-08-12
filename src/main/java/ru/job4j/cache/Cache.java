package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (s, a) -> {
            if (a.getVersion() != model.getVersion()) {
                throw new OptimisticException("Апдейт не удался!");
            }
            Base base = new Base(model.getId(), a.getVersion() + 1);
            base.setName(a.getName());
            return base;
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Map<Integer, Base> getMemory() {
        return memory;
    }
}
