package dev.imlukas.megavouchers.element.impl.generic.impl.conditions.impl.withdraw;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.impl.generic.impl.conditions.ConditionalVoucherElement;
import dev.imlukas.megavouchers.element.impl.generic.impl.conditions.type.ConditionalType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class WithdrawableConditionalVoucherElement extends ConditionalVoucherElement {
    protected WithdrawableConditionalVoucherElement(MegaVouchersPlugin plugin, ConfigurationSection section) {
        super(plugin, section);

        this.type = ConditionalType.valueOf(section.getString("condition-type", "CLAIM"));
    }

    @Override
    protected void execute(Player player) {

    }

    @Override
    public boolean meetsRequirements(Player player) {
        return false;
    }
}
