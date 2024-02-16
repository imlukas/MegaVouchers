package dev.imlukas.megavouchers;

import dev.imlukas.megavouchers.util.file.messages.Messages;
import dev.imlukas.megavouchers.element.generic.registry.GenericElementRegistry;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import lombok.Getter;

@Getter
public final class MegaVouchersPlugin extends ManagedJavaPlugin {

    private Messages messages;
    private GenericElementRegistry elementRegistry;

    @Override
    public void onLoad() {
        super.onLoad();
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).silentLogs(true));
    }

    @Override
    public void enable() {
        CommandAPI.onEnable();
        registerListeners();
        registerCommands();

        messages = new Messages(this);
        elementRegistry = new GenericElementRegistry(this);
    }

    @Override
    public void afterEnable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void registerListeners() {

    }

    @Override
    public void registerCommands() {

    }
}
