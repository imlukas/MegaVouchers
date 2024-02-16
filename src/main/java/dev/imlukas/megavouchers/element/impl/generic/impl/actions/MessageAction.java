package dev.imlukas.megavouchers.element.impl.generic.impl.actions;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.VoucherElement;
import dev.imlukas.megavouchers.util.text.TextUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MessageAction extends VoucherElement {

    private final List<String> messages = new ArrayList<>();

    public MessageAction(MegaVouchersPlugin plugin, ConfigurationSection section) {
        super(plugin, section);

        if (!section.isList("messages")) {
            messages.add(section.getString("messages"));
            return;
        }

        messages.addAll(section.getStringList("messages"));
    }

    @Override
    public void execute(Player player) {
        for (String message : messages) {

            player.sendMessage(TextUtils.color(message));
        }
    }
}
