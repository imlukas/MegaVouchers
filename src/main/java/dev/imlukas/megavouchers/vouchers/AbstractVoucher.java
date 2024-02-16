package dev.imlukas.megavouchers.vouchers;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.util.pdc.ItemPDCWrapper;
import dev.imlukas.megavouchers.vouchers.data.VoucherData;
import dev.imlukas.megavouchers.element.VoucherElement;
import dev.imlukas.megavouchers.element.VoucherElements;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AbstractVoucher implements Voucher {

    protected final MegaVouchersPlugin plugin;
    protected final VoucherElements elements;
    private final ItemStack displayItem;
    private final String identifier;
    private final String displayName;

    public AbstractVoucher(MegaVouchersPlugin plugin, VoucherData data) {
        this.plugin = plugin;
        this.elements = data.getElements();
        this.identifier = data.getIdentifier();
        this.displayName = data.getDisplayName();
        this.displayItem = data.getDisplayItem();
        ItemPDCWrapper.modifyItem(plugin, displayItem, wrapper -> wrapper.setString("voucher", identifier));
    }

    public ItemStack getDisplayItem() {
        return displayItem.clone();
    }

    @Override
    public void give(Player player) {
        player.getInventory().addItem(displayItem);
    }

    @Override
    public void claim(Player player) {
        elements.runElements(player);
    }
}
