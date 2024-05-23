package dev.imlukas.megavouchers.storage.database;

import dev.imlukas.megavouchers.vouchers.Voucher;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface VirtualVoucherStorage {

    CompletableFuture<Void> saveVoucher(Player player, Voucher voucher);

    CompletableFuture<List<Voucher>> fetchVouchers(Player player);

    CompletableFuture<Void> addQuantity(Player player, Voucher voucher, int quantity);

    CompletableFuture<Void> removeQuantity(Player player, Voucher voucher, int quantity);

    CompletableFuture<Void> deleteVoucher(Player player, String voucherId);
}
