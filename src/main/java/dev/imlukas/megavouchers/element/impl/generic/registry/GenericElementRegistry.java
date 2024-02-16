package dev.imlukas.megavouchers.element.impl.generic.registry;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.VoucherElement;
import dev.imlukas.megavouchers.element.impl.generic.impl.conditions.impl.withdraw.impl.HpWithdrawAction;
import dev.imlukas.megavouchers.element.impl.generic.impl.conditions.impl.withdraw.impl.MoneyWithdrawAction;
import dev.imlukas.megavouchers.element.impl.generic.impl.conditions.impl.withdraw.impl.XpWithdrawAction;
import dev.imlukas.megavouchers.element.impl.generic.provider.GenericProvider;
import dev.imlukas.megavouchers.element.impl.generic.impl.actions.MessageAction;
import dev.imlukas.megavouchers.element.impl.generic.impl.actions.sound.SoundAction;
import dev.imlukas.megavouchers.element.impl.generic.impl.conditions.impl.PermissionCondition;
import dev.imlukas.megavouchers.element.impl.generic.impl.actions.commands.CommandAction;
import dev.imlukas.megavouchers.element.impl.generic.impl.rewards.MoneyReward;
import dev.imlukas.megavouchers.element.impl.generic.impl.rewards.item.ItemReward;
import dev.imlukas.megavouchers.element.registry.VoucherElementRegistry;
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
        register("xp", MoneyReward::new);

        register("withdraw-money", MoneyWithdrawAction::new);
        register("withdraw-xp", XpWithdrawAction::new);
        register("withdraw-hp", HpWithdrawAction::new);
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
