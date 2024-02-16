package dev.imlukas.megavouchers;

import dev.imlukas.megavouchers.util.file.messages.Messages;
import dev.imlukas.megavouchers.element.impl.generic.registry.GenericElementRegistry;
import dev.imlukas.megavouchers.vouchers.parser.VoucherParser;
import dev.imlukas.megavouchers.vouchers.registry.VoucherRegistry;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import lombok.Getter;

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
        registerListeners();
        registerCommands();

        messages = new Messages(this);
        voucherRegistry = new VoucherRegistry();
        elementRegistry = new GenericElementRegistry(this);


        VoucherParser voucherParser = new VoucherParser(this);
        voucherParser.parseAll().forEach(voucherRegistry::registerVoucher);
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
