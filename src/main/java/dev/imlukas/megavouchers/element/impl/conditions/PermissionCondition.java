package dev.imlukas.megavouchers.element.impl.conditions;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.util.file.messages.Message;
import dev.imlukas.megavouchers.util.file.messages.impl.ChatMessage;
import dev.imlukas.megavouchers.util.file.messages.impl.RawMessage;
import dev.imlukas.megavouchers.element.VoucherElement;
import dev.imlukas.megavouchers.element.priority.VoucherElementPriority;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PermissionCondition extends VoucherElement {

    private final List<String> permissions = new ArrayList<>();
    private final Message message;

    public PermissionCondition(MegaVouchersPlugin plugin, ConfigurationSection section) {
        super(plugin, section);

        message = section.contains("message")
                ? plugin.getMessages().getMessage(section.getString("message"))
                : new RawMessage("<red>You do not have the required permissions to use this voucher.");

        if (!section.isList("permissions") && !section.isList("permission")) {
            permissions.add(section.getString("permissions", section.getString("permission")));
            return;
        }


        if (section.isList("permissions")) {
            permissions.addAll(section.getStringList("permissions"));
        } else {
            permissions.addAll(section.getStringList("permission"));
        }
    }

    @Override
    public VoucherElementPriority getPriority() {
        return VoucherElementPriority.HIGH;
    }

    @Override
    public void execute(Player player) {
        for (String permission : permissions) {
            if (!player.hasPermission(permission)) {
                return;
            }
        }
    }
}
