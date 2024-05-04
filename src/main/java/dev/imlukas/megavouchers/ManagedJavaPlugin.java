package dev.imlukas.megavouchers;

import dev.imlukas.megavouchers.util.concurrency.MainThreadExecutor;
import dev.imlukas.megavouchers.util.io.FileUtils;
import dev.imlukas.megavouchers.util.menu.registry.MenuRegistry;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Instantiates registries and other important classes that will not be changed during the plugin's lifetime.
 */
@Getter
public abstract class ManagedJavaPlugin extends JavaPlugin {

    protected static Logger log;
    protected Economy economy;
    private MenuRegistry menuRegistry;

    public static Logger getLog() {
        return log;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        MainThreadExecutor.init(this);
        saveDefaultConfig();
        FileUtils.copyBuiltInResources(this, getFile());

        setupEconomy();
        menuRegistry = new MenuRegistry(this);
        log = getLogger();

        this.enable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.disable();
    }

    public abstract void enable();

    /**
     * Runs after database, registries, and other important classes have been initialized.
     */
    public void afterEnable() {
    }

    public void disable() {
    }

    public abstract void registerListeners();

    public abstract void registerCommands();

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    public void reload() {
        HandlerList.unregisterAll(this);
        registerListeners();
    }
}
