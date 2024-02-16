package dev.imlukas.megavouchers;

import dev.imlukas.megavouchers.listener.VoucherInteractListener;
import dev.imlukas.megavouchers.util.file.messages.Messages;
import dev.imlukas.megavouchers.element.impl.generic.registry.GenericElementRegistry;
import dev.imlukas.megavouchers.vouchers.Voucher;
import dev.imlukas.megavouchers.vouchers.parser.VoucherParser;
import dev.imlukas.megavouchers.vouchers.registry.VoucherRegistry;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public final class MegaVouchersPlugin extends ManagedJavaPlugin {

    private Messages messages;
    private VoucherRegistry voucherRegistry;
    private GenericElementRegistry elementRegistry;

    @Override
    public void onLoad() {
        super.onLoad();
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).silentLogs(true));
    }

    @Override
    public void enable() {
        CommandAPI.onEnable();

        messages = new Messages(this);
        voucherRegistry = new VoucherRegistry();
        elementRegistry = new GenericElementRegistry(this);


        VoucherParser voucherParser = new VoucherParser(this);
        voucherParser.parseAll().forEach(voucherRegistry::registerVoucher);


        registerListeners();
        registerCommands();
    }

    @Override
    public void afterEnable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new VoucherInteractListener(this), this);
    }

    @Override
    public void registerCommands() {

        new CommandAPICommand("voucher").withArguments(new LiteralArgument("give"))
                .withArguments(new StringArgument("voucher")
                        .replaceSuggestions(ArgumentSuggestions.strings(voucherRegistry.getVoucherIds())))
                .executesPlayer((sender, ctx) -> {
                    String voucherId = ctx.getUnchecked("voucher");
                    voucherRegistry.getVoucher(voucherId).ifPresent(voucher -> voucher.give(sender));

                }).register();
        log.info("Registered /voucher give command");
    }
}
