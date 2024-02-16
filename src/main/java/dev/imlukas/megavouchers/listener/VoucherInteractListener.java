package dev.imlukas.megavouchers.listener;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.util.pdc.ItemPDCWrapper;
import dev.imlukas.megavouchers.vouchers.registry.VoucherRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class VoucherInteractListener implements Listener {
    private final MegaVouchersPlugin plugin;
    private final VoucherRegistry registry;

    public VoucherInteractListener(MegaVouchersPlugin plugin) {
        this.plugin = plugin;
        this.registry = plugin.getVoucherRegistry();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (item == null) {
            return;
        }

        ItemPDCWrapper wrapper = new ItemPDCWrapper(plugin, item);

        if (!wrapper.has("voucher")) {
            return;
        }

        String voucherId = wrapper.getString("voucher");

        if (voucherId == null) {
            return;
        }

        registry.getVoucher(voucherId).ifPresent(voucher -> {
            voucher.claim(event.getPlayer());

            if (item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
            } else {
                event.getPlayer().getInventory().remove(item);
            }
        });
    }
}
