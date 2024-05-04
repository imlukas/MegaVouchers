package dev.imlukas.megavouchers.commands;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.menu.VoucherListMenu;
import dev.imlukas.megavouchers.util.commands.audience.bukkit.BukkitPlayerCommandAudience;
import dev.imlukas.megavouchers.util.commands.bukkit.BukkitCommandManager;

public class VoucherListCommand {

    public VoucherListCommand(MegaVouchersPlugin plugin) {

        // TODO: Custom messages and Sounds!
        BukkitCommandManager commandManager = plugin.getCommandManager();

        commandManager.newCommand("voucher")
                .permission("megavouchers.list")
                .audience(BukkitPlayerCommandAudience.class)
                .argument("list")
                .handler((sender, context) -> new VoucherListMenu(plugin, sender.getPlayer()).open())
                .build();

    }
}
