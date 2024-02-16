package dev.imlukas.megavouchers.element.impl.generic.impl.conditions;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.VoucherElement;
import dev.imlukas.megavouchers.element.impl.generic.impl.conditions.type.ConditionalType;
import dev.imlukas.megavouchers.element.priority.VoucherElementPriority;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

@Getter
public abstract class ConditionalVoucherElement extends VoucherElement {

    protected ConditionalType type;

    protected ConditionalVoucherElement(MegaVouchersPlugin plugin, ConfigurationSection section) {
        super(plugin, section);
        this.type = ConditionalType.valueOf(section.getString("condition-type", "GIVE"));
    }

    public abstract boolean meetsRequirements(Player player);
}
