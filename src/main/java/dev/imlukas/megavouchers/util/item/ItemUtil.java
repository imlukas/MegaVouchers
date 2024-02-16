package dev.imlukas.megavouchers.util.item;

import dev.imlukas.megavouchers.util.text.ComponentPlaceholder;
import dev.imlukas.megavouchers.util.text.TextUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public final class ItemUtil {
    private static final List<String> FORGE_COMPATIBLE = List.of("pickaxe", "axe", "shovel", "hoe", "sword", "bow", "helmet", "chestplate", "leggings", "boots");

    public static boolean isForgeCompatible(ItemStack item) {
        for (String tool : FORGE_COMPATIBLE) {
            if (!item.getType().name().contains(tool.toUpperCase(Locale.ROOT))) {
                return true;
            }
        }
        return false;
    }

    public static void setItemName(ItemStack itemStack, String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(TextUtils.color(name));
        itemStack.setItemMeta(meta);
    }

    public static void clearLore(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.lore(null);
        itemStack.setItemMeta(meta);
    }

    public static void setLore(ItemStack itemStack, List<String> lore) {
        ItemMeta meta = itemStack.getItemMeta();

        meta.lore(TextUtils.color(lore));
        itemStack.setItemMeta(meta);
    }


    public static void addLore(ItemStack itemStack, List<String> lore) {
        addLore(itemStack, lore.toArray(new String[0]));
    }

    public static void addLore(ItemStack itemStack, String... toAdd) {
        ItemMeta meta = itemStack.getItemMeta();

        List<Component> lore = meta.lore();
        List<Component> toAddComponent = TextUtils.color(Arrays.asList(toAdd));

        if (lore == null) {
            lore = new ArrayList<>();
        }

        lore.addAll(toAddComponent);
        meta.lore(lore);
        itemStack.setItemMeta(meta);
    }


    public static void give(Player player, ItemStack item) {
        PlayerInventory inv = player.getInventory();

        for (Map.Entry<Integer, ItemStack> entry : inv.addItem(item).entrySet()) {
            ItemStack copy = entry.getValue().clone();
            item.setAmount(entry.getKey());
            player.getWorld().dropItemNaturally(player.getLocation(), copy);
        }
    }

    public static void setModelData(ItemStack item, int modelData) {
        ItemMeta meta = item.getItemMeta();

        meta.setCustomModelData(modelData);
        item.setItemMeta(meta);
    }

    public static <T> void replacePlaceholder(ItemStack item, T replacementObject,
                                              Collection<ComponentPlaceholder<T>> placeholderCollection) {
        if (item == null || item.getItemMeta() == null) {
            return;
        }

        if (placeholderCollection == null || placeholderCollection.isEmpty()) {
            return;
        }

        ComponentPlaceholder<T>[] placeholders = new ComponentPlaceholder[placeholderCollection.size()];

        int index = 0;
        for (ComponentPlaceholder<T> placeholder : placeholderCollection) {
            if (placeholder == null) {
                continue;
            }

            placeholders[index++] = placeholder;
        }

        // shrink array to fit
        if (index != placeholders.length) {
            ComponentPlaceholder<T>[] newPlaceholders = new ComponentPlaceholder[index];
            System.arraycopy(placeholders, 0, newPlaceholders, 0, index);
            placeholders = newPlaceholders;
        }

        replacePlaceholder(item, replacementObject, placeholders);
    }

    @SafeVarargs
    public static synchronized <T> void replacePlaceholder(ItemStack item, T replacementObject, ComponentPlaceholder<T>... placeholder) {
        if (item == null) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        if (meta.hasDisplayName()) {
            Component displayName = meta.displayName();

            for (ComponentPlaceholder<T> placeholder1 : placeholder) {
                displayName = placeholder1.replace(displayName, replacementObject);
            }

            meta.displayName(displayName);
        }

        if (meta.hasLore()) {
            List<Component> lore = meta.lore();

            for (int index = 0; index < lore.size(); index++) {
                Component line = lore.get(index);

                for (ComponentPlaceholder<T> placeholder1 : placeholder) {
                    line = placeholder1.replace(line, replacementObject);
                }

                lore.set(index, line);
            }

            meta.lore(lore);
        }
        item.setItemMeta(meta);
    }

    public static ItemStack setGlowing(ItemStack displayItem, boolean glowing) {
        if (!glowing) {
            displayItem.removeEnchantment(Enchantment.LOYALTY);
            return displayItem;
        }
        ItemMeta meta = displayItem.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.LOYALTY, 123, true);
        displayItem.setItemMeta(meta);
        return displayItem;
    }
}
