package dev.imlukas.megavouchers.element.generic.registry;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.VoucherElement;
import dev.imlukas.megavouchers.element.generic.provider.GenericProvider;
import dev.imlukas.megavouchers.element.impl.actions.MessageAction;
import dev.imlukas.megavouchers.element.impl.actions.sound.SoundAction;
import dev.imlukas.megavouchers.element.impl.conditions.PermissionCondition;
import dev.imlukas.megavouchers.element.impl.actions.commands.CommandAction;
import dev.imlukas.megavouchers.element.impl.rewards.MoneyReward;
import dev.imlukas.megavouchers.element.impl.rewards.item.ItemReward;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GenericElementRegistry implements VoucherElementRegistry {

    private final MegaVouchersPlugin plugin;
    private final Map<String, GenericProvider> registeredProviders = new ConcurrentHashMap<>();

    public GenericElementRegistry(MegaVouchersPlugin plugin) {
        this.plugin = plugin;
        registerDefaults();
    }

    public void register(List<String> ids, GenericProvider provider) {
        for (String id : ids) {
            register(id, provider);
        }
    }

    public void register(String id, GenericProvider provider) {
        registeredProviders.put(id, provider);
    }

    @Override
    public void registerDefaults() {
        register(List.of("permissions", "permission"), PermissionCondition::new);

        register(List.of("messages", "message"), MessageAction::new);
        register(List.of("sounds", "sound"), SoundAction::new);

        register(List.of("items", "item"), ItemReward::new);
        register(List.of("commands", "command"), CommandAction::new);
        register("money", MoneyReward::new);
    }

    @Override
    public VoucherElement tryParse(ConfigurationSection section) {
        String id = section.contains("type") ? section.getString("type") : section.getName();
        GenericProvider provider = registeredProviders.get(id);

        if (provider == null) {
            return null;
        }

        return provider.provide(plugin, section);
    }
}
