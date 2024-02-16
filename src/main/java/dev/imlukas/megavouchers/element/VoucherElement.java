package dev.imlukas.megavouchers.element;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.priority.VoucherElementPriority;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * Tag interface for all cracker objects
 */
public abstract class VoucherElement {

    protected VoucherElementPriority priority = VoucherElementPriority.NORMAL;

    protected VoucherElement(MegaVouchersPlugin plugin, ConfigurationSection section) {
        if (section.contains("priority")) {
            this.priority = VoucherElementPriority.valueOf(section.getString("priority"));
        }
    }

    public VoucherElementPriority getPriority() {
        return priority;
    }

    /**
     * Executes the action
     * @param player the player to execute the action on
     * @return true if the action was successful, false otherwise
     */
    protected abstract void execute(Player player);
}
