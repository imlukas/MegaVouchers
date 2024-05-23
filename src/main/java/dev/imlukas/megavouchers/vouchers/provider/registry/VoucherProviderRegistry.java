package dev.imlukas.megavouchers.vouchers.provider.registry;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.registry.ProviderRegistry;
import dev.imlukas.megavouchers.vouchers.Voucher;
import dev.imlukas.megavouchers.vouchers.data.VoucherData;
import dev.imlukas.megavouchers.vouchers.impl.ConfigurableVoucher;
import dev.imlukas.megavouchers.vouchers.impl.VirtualConfigurableVoucher;
import dev.imlukas.megavouchers.vouchers.provider.VoucherProvider;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Locale;

public class VoucherProviderRegistry extends ProviderRegistry<VoucherProvider> {

    private final MegaVouchersPlugin plugin;

    public VoucherProviderRegistry(MegaVouchersPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void registerDefaults() {
        register("physical", ConfigurableVoucher::new);
        register("virtual", VirtualConfigurableVoucher::new);
    }

    public Voucher tryParse(VoucherData data) {
        VoucherProvider provider = get(data.getType().toLowerCase(Locale.ROOT));

        if (provider == null) {
            return null;
        }

        return provider.provide(plugin, data);
    }
}
