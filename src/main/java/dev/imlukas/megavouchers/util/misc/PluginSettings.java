package dev.imlukas.megavouchers.util.misc;

import dev.imlukas.megavouchers.ManagedJavaPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
@Setter
public class PluginSettings {

    private final FileConfiguration config;

    public PluginSettings(ManagedJavaPlugin plugin) {
        this.config = plugin.getConfig();
        load();
    }

    public void load() {
    }
}
