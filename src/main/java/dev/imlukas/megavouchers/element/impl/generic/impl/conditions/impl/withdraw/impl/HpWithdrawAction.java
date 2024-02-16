package dev.imlukas.megavouchers.element.impl.generic.impl.conditions.impl.withdraw.impl;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.impl.generic.impl.conditions.impl.withdraw.WithdrawableConditionalVoucherElement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class HpWithdrawAction extends WithdrawableConditionalVoucherElement {

    private final double amount;

    public HpWithdrawAction(MegaVouchersPlugin plugin, ConfigurationSection section) {
        super(plugin, section);
        this.amount = section.getDouble("value");
    }

    @Override
    public boolean meetsRequirements(Player player) {
        return player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getValue() <= amount;
    }

    @Override
    protected void execute(Player player) {
        //
    }
}
