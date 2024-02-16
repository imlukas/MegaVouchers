package dev.imlukas.megavouchers.element.impl.generic.impl.conditions.impl;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.impl.generic.impl.conditions.ConditionalVoucherElement;
import dev.imlukas.megavouchers.util.file.messages.Message;
import dev.imlukas.megavouchers.util.file.messages.impl.RawMessage;
import dev.imlukas.megavouchers.element.priority.VoucherElementPriority;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PermissionCondition extends ConditionalVoucherElement {

    private final List<String> permissions = new ArrayList<>();
    private Message message;

    public PermissionCondition(MegaVouchersPlugin plugin, ConfigurationSection section) {
        super(plugin, section);

        if (section.contains("message")) {
            if (section.isConfigurationSection("message")) {
                message = plugin.getMessages().getMessage(section.getConfigurationSection("message"));
            } else {
                message = new RawMessage(section.getString("message", "<red> You do not have permission to use this voucher!"));
            }
        }

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
    public boolean meetsRequirements(Player player) {
        for (String permission : permissions) {
            if (!player.hasPermission(permission)) {
                if (message != null) {
                    message.send(player);
                }
                return false;
            }
        }

        return true;
    }

    @Override
    public void execute(Player player) {
        // Do nothing
    }
}
