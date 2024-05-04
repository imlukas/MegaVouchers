package dev.imlukas.megavouchers.vouchers.data;

import dev.imlukas.megavouchers.element.container.VoucherElementContainer;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data
public class VoucherData {

    private final String identifier;
    private final ItemStack displayItem;
    private final VoucherElementContainer elements;
}
