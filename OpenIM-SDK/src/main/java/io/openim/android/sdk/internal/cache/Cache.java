package io.openim.android.sdk.internal.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.BiPredicate;
import java.util.ArrayList;
import java.util.List;

public class Cache<K, V> {

    private ConcurrentHashMap<K, V> map;

    public Cache() {
        this.map = new ConcurrentHashMap<>();
    }

    // Load returns the value stored in the map for a key, or null if no value is present.
    public V load(K key) {
        return map.get(key);
    }

    // Store sets the value for a key.
    public void store(K key, V value) {
        map.put(key, value);
    }

    // StoreAll sets all values by f's key.
    public void storeAll(Function<V, K> f, List<V> values) {
        values.forEach(v -> map.put(f.apply(v), v));
    }

    // LoadOrStore returns the existing value for the key if present.
    public V loadOrStore(K key, V value) {
        return map.compute(key, (k, v) -> v == null ? value : v);
    }

    // Delete deletes the value for a key.
    public void delete(K key) {
        map.remove(key);
    }

    // DeleteAll deletes all values.
    public void deleteAll() {
        map.clear();
    }

    // RangeAll returns all values in the map.
    public List<V> rangeAll() {
        return new ArrayList<>(map.values());
    }

    // RangeCon returns values in the map that satisfy condition f.
    public List<V> rangeCon(BiPredicate<K, V> f) {
        List<V> values = new ArrayList<>();
        map.forEach((k, v) -> {
            if (f.test(k, v)) {
                values.add(v);
            }
        });
        return values;
    }
}
