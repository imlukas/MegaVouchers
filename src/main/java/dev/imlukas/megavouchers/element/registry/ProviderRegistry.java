package dev.imlukas.megavouchers.element.registry;

import org.bukkit.configuration.ConfigurationSection;

public interface ProviderRegistry<T> {

    void registerDefaults();

    T tryParse(ConfigurationSection section);
}
