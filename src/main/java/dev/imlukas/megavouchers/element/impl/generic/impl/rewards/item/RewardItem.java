package dev.imlukas.megavouchers.element.impl.generic.impl.rewards.item;

import org.bukkit.inventory.ItemStack;

public class RewardItem {

    private final ItemStack itemStack;
    private final double chance;
    private final boolean shouldDrop;

    public RewardItem(ItemStack itemStack, double chance, boolean drop) {
        this.itemStack = itemStack;
        this.chance = chance;
        this.shouldDrop = drop;
    }

    public boolean shouldDrop() {
        return shouldDrop;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public double getChance() {
        return chance;
    }

}
