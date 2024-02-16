package dev.imlukas.megavouchers.element.impl.generic.provider;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.VoucherElement;
import org.bukkit.configuration.ConfigurationSection;

public interface GenericProvider {

    VoucherElement provide(MegaVouchersPlugin vouchers, ConfigurationSection section);
}
