package dev.imlukas.megavouchers.util.menu.base;

import dev.imlukas.megavouchers.util.menu.configuration.ConfigurationApplicator;
import dev.imlukas.megavouchers.util.menu.mask.PatternMask;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Getter
public class ConfigurableMenu extends BaseMenu {

    private final ConfigurationApplicator applicator;


    public ConfigurableMenu(UUID playerId, String title, int rows, ConfigurationApplicator applicator) {
        super(playerId, title, rows);
        this.applicator = applicator;
    }

    public ItemStack getItem(String key) {
        return getApplicator().getItem(key);
    }

    public PatternMask getMask() {
        return getApplicator().getMask();
    }

    public String getTitle() {
        return getApplicator().getDefaultTitle();
    }

    public ConfigurationSection getConfig() {
        return getApplicator().getConfig();
    }


}
