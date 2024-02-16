package dev.imlukas.megavouchers.element.impl.rewards;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.VoucherElement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class MoneyReward extends VoucherElement {

    // private final Economy economy;
    private final int amount;

    public MoneyReward(MegaVouchersPlugin plugin, ConfigurationSection rewardSection) {
        super(plugin, rewardSection);
        // this.economy = plugin.getEconomy();
        this.amount = rewardSection.getInt("value");
    }

    @Override
    public void execute(Player player) {
        // economy.depositPlayer(player, amount);
    }
}
