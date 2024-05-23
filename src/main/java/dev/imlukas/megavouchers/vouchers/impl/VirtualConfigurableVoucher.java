package dev.imlukas.megavouchers.vouchers.impl;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.impl.generic.impl.conditions.type.ConditionalType;
import dev.imlukas.megavouchers.storage.database.VirtualVoucherStorage;
import dev.imlukas.megavouchers.vouchers.data.VoucherData;
import org.bukkit.entity.Player;

public class VirtualConfigurableVoucher extends ConfigurableVoucher {

    private final VirtualVoucherStorage storage;
    public VirtualConfigurableVoucher(MegaVouchersPlugin plugin, VoucherData data) {
        super(plugin, data);
        this.storage = plugin.getVirtualVoucherStorage();
    }


    @Override
    public void give(Player player, int amount) {
        storage.addQuantity(player, this, amount);
    }

    @Override
    public void give(Player player) {
        if (!elements.meetsConditions(player, ConditionalType.GIVE)) {
            return;
        }

        storage.addQuantity(player, this, 1);
    }

    @Override
    public void forceGive(Player player, int amount) {
        give(player, amount);
    }

    @Override
    public void claim(Player player) {
        if (!elements.meetsConditions(player, ConditionalType.CLAIM)) {
            return;
        }

        storage.removeQuantity(player, this, 1);
        elements.runElements(player);
    }
}
