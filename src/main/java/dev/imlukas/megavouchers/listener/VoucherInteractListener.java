package dev.imlukas.megavouchers.listener;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.util.player.PlayerUtil;
import dev.imlukas.megavouchers.util.pdc.ItemPDCWrapper;
import dev.imlukas.megavouchers.vouchers.registry.VoucherRegistry;
import org.bukkit.entity.Player;
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
        Player player = event.getPlayer();
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

        registry.getAsOptional(voucherId).ifPresent(voucher -> {
            voucher.claim(player);
            PlayerUtil.removeItem(player, item);
        });
    }
}
