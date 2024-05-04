package dev.imlukas.megavouchers.util.menu.template;

import dev.imlukas.megavouchers.ManagedJavaPlugin;
import org.bukkit.entity.Player;

public abstract class UpdatableMenu extends Menu {

    protected UpdatableMenu(ManagedJavaPlugin plugin, Player viewer) {
        super(plugin, viewer);
    }

    /**
     * Handles refreshing placeholders and updating buttons and other elements accordingly.
     */
    public abstract void refresh();

    @Override
    public void open() {
        refresh();
        super.open();
    }
}
