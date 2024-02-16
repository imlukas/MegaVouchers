package dev.imlukas.megavouchers.vouchers.registry;

import dev.imlukas.megavouchers.vouchers.Voucher;
import dev.imlukas.megavouchers.vouchers.impl.PlayerVoucher;

import java.util.*;

public class VoucherRegistry {

    private final Map<String, Voucher> vouchers = new HashMap<>();

    public void registerVoucher(Voucher voucher) {
        vouchers.put(voucher.getIdentifier(), voucher);
    }

    public Voucher getVoucher(String id) {
        return vouchers.get(id);
    }

    public void unregisterVoucher(String id) {
        vouchers.remove(id);
    }

    public Set<String> getVoucherIds() {
        return Set.copyOf(vouchers.keySet());
    }

    public Map<String, Voucher> getVouchers() {
        return Map.copyOf(vouchers);
    }
}
