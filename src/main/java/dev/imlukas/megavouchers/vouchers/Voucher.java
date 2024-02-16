package dev.imlukas.megavouchers.vouchers;

import dev.imlukas.megavouchers.element.VoucherElements;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface Voucher {

    String getIdentifier();

    VoucherElements getElements();

    void give(Player player);

    void claim(Player player);


}
