package dev.imlukas.megavouchers.vouchers;

import dev.imlukas.megavouchers.element.container.VoucherElementContainer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Voucher {

    ItemStack getDisplayItem();

    String getIdentifier();

    VoucherElementContainer getElements();

    /**
     * Gives one voucher to the player, if the player meets the requirements of the voucher
     *
     * @param player the player to give the voucher to
     */
    void give(Player player);

    /**
     * Gives a certain amount of vouchers to the player, even if the player doesn't meet the requirements of the voucher
     *
     * @param player the player to give the voucher to
     * @param amount the amount of vouchers to give
     */
    void forceGive(Player player, int amount);

    /**
     * Claims the voucher for the player, if the player meets the requirements of the voucher
     *
     * @param player the player to claim the voucher for
     */
    void claim(Player player);


}
