package dev.imlukas.megavouchers.vouchers;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.impl.generic.impl.conditions.type.ConditionalType;
import dev.imlukas.megavouchers.util.pdc.ItemPDCWrapper;
import dev.imlukas.megavouchers.vouchers.data.VoucherData;
import dev.imlukas.megavouchers.element.VoucherElements;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
public abstract class AbstractVoucher implements Voucher {

    protected final MegaVouchersPlugin plugin;
    protected final VoucherElements elements;
    private final ItemStack displayItem;
    private final String identifier;

    public AbstractVoucher(MegaVouchersPlugin plugin, VoucherData data) {
        this.plugin = plugin;
        this.elements = data.getElements();
        this.identifier = data.getIdentifier();
        this.displayItem = data.getDisplayItem();
        ItemPDCWrapper.modifyItem(plugin, displayItem, wrapper -> wrapper.setString("voucher", identifier));
    }

    public ItemStack getDisplayItem() {
        return displayItem.clone();
    }

    @Override
    public void give(Player player) {
        if (!elements.runConditionals(player, ConditionalType.GIVE)) { // Players doesnt meet requirements to get this voucher
            return;
        }

        player.getInventory().addItem(displayItem);
    }

    @Override
    public void claim(Player player) {
        if (!elements.runConditionals(player, ConditionalType.CLAIM)) {
            return;
        }
        elements.runElements(player);
    }
}
