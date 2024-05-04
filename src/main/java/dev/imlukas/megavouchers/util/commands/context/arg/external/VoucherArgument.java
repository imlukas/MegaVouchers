package dev.imlukas.megavouchers.util.commands.context.arg.external;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.util.commands.context.CommandContext;
import dev.imlukas.megavouchers.util.commands.context.arg.AbstractArgument;
import dev.imlukas.megavouchers.vouchers.Voucher;
import dev.imlukas.megavouchers.vouchers.registry.VoucherRegistry;

import java.util.ArrayList;
import java.util.List;

public class VoucherArgument extends AbstractArgument<Voucher> {

    private final VoucherRegistry voucherRegistry;

    protected VoucherArgument(MegaVouchersPlugin plugin, String name) {
        super(name);
        this.voucherRegistry = plugin.getVoucherRegistry();
    }

    public static VoucherArgument create(MegaVouchersPlugin plugin, String name) {
        return new VoucherArgument(plugin, name);
    }

    @Override
    public List<String> tabComplete(CommandContext context) {
        return new ArrayList<>(voucherRegistry.getKeys());
    }

    @Override
    public Voucher parse(CommandContext context) {
        return voucherRegistry.get(context.getLastInput());
    }
}
