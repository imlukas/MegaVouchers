package dev.imlukas.megavouchers.util.player;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public final class PlayerUtil {

    private PlayerUtil() {
    }

    public static void removeItem(Player player, ItemStack itemStack) {
        if (itemStack.getAmount() > 1) {
            itemStack.setAmount(itemStack.getAmount() - 1);
        } else {
            player.getInventory().remove(itemStack);
        }

    }

    public static void giveItem(Player player, ItemStack itemStack, int amount) {
        int maxStackSize = itemStack.getMaxStackSize();

        if (amount > maxStackSize) {
            int iterations = amount / maxStackSize;

            for (int i = 0; i < iterations; i++) {
                giveAndDrop(player, itemStack, maxStackSize);
            }
            return;
        }

        giveAndDrop(player, itemStack, amount);
    }

    private static void giveAndDrop(Player player, ItemStack itemStack, int amount) {
        ItemStack toGive = itemStack.clone();
        toGive.setAmount(amount);
        dropRemainingItems(player, player.getInventory().addItem(toGive));
    }

    public static void giveItem(Player player, ItemStack itemStack) {
        giveItem(player, itemStack, true);
    }

    public static void giveItem(Player player, ItemStack itemStack, boolean drop) {
        Map<Integer, ItemStack> remainingItems = player.getInventory().addItem(itemStack);

        if (!drop) {
            return;
        }

        dropRemainingItems(player, remainingItems);
    }

    private static void dropRemainingItems(Player player, Map<Integer, ItemStack> items) {
        World world = player.getWorld();
        Location location = player.getLocation();

        for (ItemStack value : items.values()) {
            world.dropItem(location, value);
        }
    }
}
