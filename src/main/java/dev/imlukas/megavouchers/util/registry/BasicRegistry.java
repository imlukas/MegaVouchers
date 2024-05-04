package dev.imlukas.megavouchers.util.registry;

import java.util.*;

/**
 * A basic implementation of a registry. AKA a map wrapper with some utility methods.
 *
 * @param <K> Key type
 * @param <V> Value type
 */
public class BasicRegistry<K, V> extends HashMap<K, V> implements Registry<K, V> {

    @Override
    public void register(K key, V value) {
        put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T extends V> T getTyped(K key) {
        return (T) get(key);
    }

    public Optional<V> getAsOptional(K key) {
        return Optional.ofNullable(get(key));
    }

    @Override
    public void unregister(K key) {
        remove(key);
    }

    @Override
    public Map<K, V> getRegistered() {
        return Map.copyOf(this);
    }

    @Override
    public boolean isRegistered(K key) {
        return containsKey(key);
    }

    @Override
    public List<V> getValues() {
        return List.copyOf(values());
    }

    @Override
    public Set<K> getKeys() {
        return Set.copyOf(keySet());
    }

}
