package dev.imlukas.megavouchers.util.item.parser.parsers;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public final class VanillaItemParser {
    private VanillaItemParser() {
    }

    public static ItemStack parse(ConfigurationSection section) {
        Material material = Material.getMaterial(section.getString("material", section.getString("type", "PAPER")).toUpperCase());

        if (material == null) {
            return null;
        }

        return new ItemStack(material);
    }
}
