package dev.imlukas.megavouchers.element;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.priority.VoucherElementPriority;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * Tag interface for all cracker objects
 */
public abstract class VoucherElement {

    protected VoucherElement(MegaVouchersPlugin plugin, ConfigurationSection section) {}

    public VoucherElementPriority getPriority() {
        return VoucherElementPriority.NORMAL;
    }

    protected abstract void execute(Player player);
}
