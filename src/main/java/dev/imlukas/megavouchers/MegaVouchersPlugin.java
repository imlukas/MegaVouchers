package dev.imlukas.megavouchers;

import dev.imlukas.megavouchers.commands.VoucherGiveAndGetCommands;
import dev.imlukas.megavouchers.commands.VoucherListCommand;
import dev.imlukas.megavouchers.element.impl.generic.registry.ElementProviderRegistry;
import dev.imlukas.megavouchers.listener.VoucherInteractListener;
import dev.imlukas.megavouchers.storage.database.VirtualVoucherStorage;
import dev.imlukas.megavouchers.util.commands.bukkit.BukkitCommandManager;
import dev.imlukas.megavouchers.util.file.messages.Messages;
import dev.imlukas.megavouchers.vouchers.parser.VoucherParser;
import dev.imlukas.megavouchers.vouchers.registry.VoucherRegistry;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

@Getter
public final class MegaVouchersPlugin extends ManagedJavaPlugin {

    private Messages messages;
    private BukkitCommandManager commandManager;

    private VoucherRegistry voucherRegistry;
    private ElementProviderRegistry elementRegistry;

    @Getter(AccessLevel.NONE)
    private VoucherParser voucherParser;

    private VirtualVoucherStorage virtualVoucherStorage;

    @Override
    public void enable() {
        messages = new Messages(this);
        commandManager = new BukkitCommandManager(this, messages);

        voucherRegistry = new VoucherRegistry();
        elementRegistry = new ElementProviderRegistry(this);

        voucherParser = new VoucherParser(this);
        voucherParser.parseAll().forEach(voucherRegistry::register);

        registerListeners();
        registerCommands();
    }

    @Override
    public void registerListeners() {
        registerListener(new VoucherInteractListener(this));
    }

    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void registerCommands() {
        new VoucherGiveAndGetCommands(this);
        new VoucherListCommand(this);
    }

    public void reload() {
        super.reload();
        messages.reload();
        voucherRegistry.clear();
        voucherParser.parseAll().forEach(voucherRegistry::register);
    }

}

