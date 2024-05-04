package dev.imlukas.megavouchers.util.menu.template;

import dev.imlukas.megavouchers.ManagedJavaPlugin;
import dev.imlukas.megavouchers.util.concurrency.MainThreadExecutor;
import dev.imlukas.megavouchers.util.menu.base.ConfigurableBaseMenu;
import dev.imlukas.megavouchers.util.menu.configuration.ConfigurationApplicator;
import dev.imlukas.megavouchers.util.menu.layer.BaseLayer;
import dev.imlukas.megavouchers.util.menu.mask.PatternMask;
import dev.imlukas.megavouchers.util.menu.registry.MenuRegistry;
import dev.imlukas.megavouchers.util.menu.registry.meta.HiddenMenuTracker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.function.Consumer;

public abstract class Menu {

    protected final JavaPlugin plugin;
    protected final ConfigurationApplicator applicator;
    protected final PatternMask mask;
    protected final ConfigurableBaseMenu menu;
    private final MenuRegistry menuRegistry;
    private final HiddenMenuTracker hiddenMenuTracker;
    private final UUID viewerId;

    protected Menu(ManagedJavaPlugin plugin, Player viewer) {
        this.plugin = plugin;
        this.menuRegistry = plugin.getMenuRegistry();
        this.hiddenMenuTracker = menuRegistry.getHiddenMenuTracker();
        this.viewerId = viewer.getUniqueId();
        this.menu = createMenu();
        this.applicator = menu.getApplicator();
        this.mask = applicator.getMask();
        BaseLayer defaultLayer = new BaseLayer(menu);
        applicator.setDefaultLayer(defaultLayer);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    /**
     * Handles creation of the menu, definition of variables and static button creation.
     */
    public abstract void setup();

    public void open() {
        menu.open();
    }

    public void close() {
        Player viewer = getViewer();

        if (viewer.getOpenInventory().getTopInventory().equals(menu.getInventory())) {
            if (Bukkit.isPrimaryThread()) {
                viewer.closeInventory();
            } else {
                MainThreadExecutor.INSTANCE.execute(viewer::closeInventory); // fuck you bukkit
            }
        }
    }

    public ConfigurableBaseMenu createMenu() {
        return (ConfigurableBaseMenu) menuRegistry.create(getIdentifier(), getViewer());
    }

    public abstract String getIdentifier();

    public Player getViewer() {
        return Bukkit.getPlayer(viewerId);
    }

    public UUID getViewerId() {
        return viewerId;
    }

    public ConfigurableBaseMenu getMenu() {
        return menu;
    }

    public Menu onClose(Runnable onClose) {
        menu.onClose(onClose);
        return this;
    }

    public void holdForInput(Consumer<String> action) {
        holdForInput(action, true);
    }

    public void holdForInput(Consumer<String> action, boolean reopenMenu) {
        hiddenMenuTracker.holdForInput(menu, action, reopenMenu);
    }
}
