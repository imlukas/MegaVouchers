package dev.imlukas.megavouchers.vouchers.impl;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.util.pdc.ItemPDCWrapper;
import dev.imlukas.megavouchers.vouchers.AbstractVoucher;
import dev.imlukas.megavouchers.vouchers.data.VoucherData;
import org.bukkit.inventory.ItemStack;

public class PlayerVoucher extends AbstractVoucher {

    public PlayerVoucher(MegaVouchersPlugin plugin, VoucherData data) {
        super(plugin, data);


    }
}
