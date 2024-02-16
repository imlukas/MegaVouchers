package dev.imlukas.megavouchers.element.impl.generic.impl.conditions.impl.withdraw.impl;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.impl.generic.impl.conditions.impl.withdraw.WithdrawableConditionalVoucherElement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class MoneyWithdrawAction extends WithdrawableConditionalVoucherElement {
    public MoneyWithdrawAction(MegaVouchersPlugin plugin, ConfigurationSection section) {
        super(plugin, section);
    }

    @Override
    public boolean meetsRequirements(Player player) {
        return true;
    }

    @Override
    protected void execute(Player player) {

    }
}
