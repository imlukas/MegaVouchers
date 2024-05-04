package dev.imlukas.megavouchers.commands;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.util.commands.audience.bukkit.BukkitPlayerCommandAudience;
import dev.imlukas.megavouchers.util.commands.bukkit.BukkitCommandManager;
import dev.imlukas.megavouchers.util.commands.context.CommandContext;
import dev.imlukas.megavouchers.util.commands.context.arg.IntegerArgument;
import dev.imlukas.megavouchers.util.commands.context.arg.PlayerArgument;
import dev.imlukas.megavouchers.util.commands.context.arg.external.VoucherArgument;
import dev.imlukas.megavouchers.vouchers.Voucher;
import org.bukkit.entity.Player;

public class VoucherGiveAndGetCommands {

    public VoucherGiveAndGetCommands(MegaVouchersPlugin plugin) {

        // TODO: Custom messages and Sounds!
        BukkitCommandManager commandManager = plugin.getCommandManager();

        commandManager.newCommand("voucher")
                .permission("megavouchers.admin.give")
                .argument("give")
                .argument(PlayerArgument.create("player"))
                .argument(VoucherArgument.create(plugin, "voucher"))
                .argument(IntegerArgument.create("amount", "1", "2", "5", "10", "20", "50", "100"))
                .handler((sender, context) -> {
                    Player target = context.getArgument("player");

                    if (target == null) {
                        return;
                    }

                    handle(target, context);
                }).build();

        commandManager.newCommand("voucher")
                .permission("megavouchers.admin.get")
                .audience(BukkitPlayerCommandAudience.class)
                .argument("get")
                .argument(VoucherArgument.create(plugin, "voucher"))
                .argument(IntegerArgument.create("amount", "1", "2", "5", "10", "20", "50", "100"))
                .handler((sender, context) -> handle(sender.getPlayer(), context))
                .build();
    }

    public void handle(Player target, CommandContext context) {
        Voucher voucher = context.getArgument("voucher");

        if (voucher == null) {
            return;
        }

        int amount = context.getArgument("amount");

        voucher.forceGive(target, amount);
    }
}
