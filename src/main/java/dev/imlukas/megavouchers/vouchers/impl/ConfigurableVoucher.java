package dev.imlukas.megavouchers.vouchers.impl;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.container.VoucherElementContainer;
import dev.imlukas.megavouchers.element.impl.generic.impl.conditions.type.ConditionalType;
import dev.imlukas.megavouchers.util.pdc.ItemPDCWrapper;
import dev.imlukas.megavouchers.util.player.PlayerUtil;
import dev.imlukas.megavouchers.vouchers.Voucher;
import dev.imlukas.megavouchers.vouchers.data.VoucherData;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
public class ConfigurableVoucher implements Voucher {

    private final MegaVouchersPlugin plugin;
    protected final VoucherElementContainer elements;
    private final ItemStack displayItem;
    private final String identifier;

    public ConfigurableVoucher(MegaVouchersPlugin plugin, VoucherData data) {
        // TODO: Add custom messages when conditionals aren't met + sounds when claiming!
        this.plugin = plugin;
        this.elements = data.getElements();
        this.identifier = data.getIdentifier();
        this.displayItem = data.getDisplayItem();
        ItemPDCWrapper.modifyItem(plugin, displayItem, wrapper -> wrapper.setString("voucher", identifier));
    }

    public void give(Player player, int amount) {
        for (int i = 0; i < amount; i++) {
            player.getInventory().addItem(displayItem.clone());
        }
    }

    @Override
    public void give(Player player) {
        if (!elements.meetsConditions(player, ConditionalType.GIVE)) {
            return;
        }

        give(player, 1);
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

        elements.runElements(player);
    }

    @Override
    public void remove(Player player, ItemStack item) {
        PlayerUtil.removeItem(player, item);
    }
}
