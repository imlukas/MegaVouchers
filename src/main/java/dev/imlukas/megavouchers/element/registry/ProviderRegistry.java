package dev.imlukas.megavouchers.element.registry;

import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public abstract class ProviderRegistry<T> {

    private final Map<String, T> providers = new HashMap<>();

    public void register(String key, T provider) {
        providers.put(key, provider);
    }

    public T get(String key) {
        return providers.get(key);
    }


    protected abstract void registerDefaults();
}
