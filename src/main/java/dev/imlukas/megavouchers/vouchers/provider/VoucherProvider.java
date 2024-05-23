package dev.imlukas.megavouchers.vouchers.provider;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.vouchers.Voucher;
import dev.imlukas.megavouchers.vouchers.data.VoucherData;

public interface VoucherProvider {

    Voucher provide(MegaVouchersPlugin plugin, VoucherData data);
}
