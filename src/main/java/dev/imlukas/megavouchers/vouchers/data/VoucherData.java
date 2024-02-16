package dev.imlukas.megavouchers.vouchers.data;

import dev.imlukas.megavouchers.element.VoucherElements;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data
public class VoucherData {

    private final String identifier;
    private final String displayName;
    private final VoucherElements elements;
    private final ItemStack displayItem;
}
