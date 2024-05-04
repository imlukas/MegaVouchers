package dev.imlukas.megavouchers.util.registry;

import java.util.Collection;
import java.util.Map;

public interface Registry<K, V> {

    void register(K key, V value);

    void unregister(K key);

    Map<K, V> getRegistered();

    void clear();

    boolean isRegistered(K key);

    Collection<V> getValues();

    Collection<K> getKeys();
}
