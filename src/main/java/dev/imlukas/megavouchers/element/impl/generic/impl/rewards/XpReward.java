package dev.imlukas.megavouchers.element.impl.generic.impl.rewards;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.VoucherElement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class XpReward extends VoucherElement {

    private final int amount;

    protected XpReward(MegaVouchersPlugin plugin, ConfigurationSection section) {
        super(plugin, section);

        this.amount = section.getInt("value");
    }

    @Override
    protected void execute(Player player) {
        player.giveExp(amount);
    }
}
