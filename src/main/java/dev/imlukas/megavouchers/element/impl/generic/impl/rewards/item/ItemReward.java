package dev.imlukas.megavouchers.element.impl.generic.impl.rewards.item;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.VoucherElement;
import dev.imlukas.megavouchers.util.item.parser.ItemParser;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static dev.imlukas.megavouchers.ManagedJavaPlugin.getLog;

public class ItemReward extends VoucherElement {

    private final List<RewardItem> items = new ArrayList<>();

    public ItemReward(MegaVouchersPlugin plugin, ConfigurationSection rewardSection) {
        super(plugin, rewardSection);
        for (String key : rewardSection.getKeys(false)) {
            if (key.equals("type") || key.equals("drop") || key.equals("chance")) {
                continue;
            }

            ConfigurationSection itemSection = rewardSection.getConfigurationSection(key);

            if (itemSection == null) {
                getLog().warning("Invalid item " + key + " in reward");
                continue;
            }

            ItemStack item = ItemParser.from(itemSection);

            if (item == null) {
                getLog().warning("Invalid item " + key + " in reward");
                continue;
            }

            int chance = itemSection.getInt("chance", 100);
            boolean drop = itemSection.getBoolean("drop", false);
            items.add(new RewardItem(item, (double) chance / 100, drop));
        }
    }

    public void reward(Player player, Location itemDropLocation) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (RewardItem item : items) {
            if (random.nextDouble() > item.getChance()) {
                continue;
            }

            if (item.shouldDrop()) {
                itemDropLocation.getWorld().dropItem(itemDropLocation, item.getItemStack());
                continue;
            }

            Collection<ItemStack> items = player.getInventory().addItem(item.getItemStack()).values();

            for (ItemStack overflow : items) {
                itemDropLocation.getWorld().dropItem(itemDropLocation, overflow);
            }
        }
    }

    @Override
    public void execute(Player player) {
        reward(player, player.getLocation());
    }
}
