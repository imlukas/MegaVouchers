package dev.imlukas.megavouchers.element.generic.registry;

import dev.imlukas.megavouchers.element.VoucherElement;
import org.bukkit.configuration.ConfigurationSection;

public interface VoucherElementRegistry {

    void registerDefaults();

    VoucherElement tryParse(ConfigurationSection section);
}
