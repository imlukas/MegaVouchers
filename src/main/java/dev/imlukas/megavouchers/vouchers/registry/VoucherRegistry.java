package dev.imlukas.megavouchers.vouchers.registry;

import dev.imlukas.megavouchers.util.registry.BasicRegistry;
import dev.imlukas.megavouchers.vouchers.Voucher;

public class VoucherRegistry extends BasicRegistry<String, Voucher> {

    public void register(Voucher voucher) {
        register(voucher.getIdentifier(), voucher);
    }
}
