package dev.imlukas.megavouchers;

import dev.imlukas.megavouchers.util.io.FileUtils;
import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Instantiates registries and other important classes that will not be changed during the plugin's lifetime.
 */
@Getter
public abstract class ManagedJavaPlugin extends JavaPlugin {

    protected static Logger log;

    @Override
    public void onEnable() {
        super.onEnable();
        saveDefaultConfig();
        FileUtils.copyBuiltInResources(this, getFile());

        log = getLogger();


        this.enable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.disable();
    }

    public static Logger getLog() {
        return log;
    }

    public abstract void enable();

    /**
     * Runs after database, registries, and other important classes have been initialized.
     */
    public abstract void afterEnable();

    public abstract void disable();

    public abstract void registerListeners();

    public abstract void registerCommands();

    public void reload() {
        HandlerList.unregisterAll(this);
        registerListeners();
    }
}
