package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    Map<Integer, Base> mapOrigin = new ConcurrentHashMap<>();

    @Test
    void add() {
        Base base = new Base(1, 0);
        mapOrigin.put(base.getId(), base);
        Cache cache = new Cache();
        cache.add(base);
        assertEquals(mapOrigin, cache.getMemory());
        assertEquals(mapOrigin.get(base.getId()), cache.getMemory().get(base.getId()));
    }

    @Test
    void updateVersion() throws Exception {
        Base base = new Base(1, 0);
        Base baseUpdated = new Base(1, 0);
        Cache cache = new Cache();
        cache.add(base);
        cache.update(baseUpdated);
        assertEquals(1, cache.getMemory().get(baseUpdated.getId()).getVersion());
    }

    @Test
    void delete() {
        Base base = new Base(1, 0);
        Cache cache = new Cache();
        cache.add(base);
        cache.delete(base);
        assertTrue(cache.getMemory().isEmpty());
    }
}