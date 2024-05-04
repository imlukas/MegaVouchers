package dev.imlukas.megavouchers.element.impl.generic.impl.actions;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.VoucherElement;
import dev.imlukas.megavouchers.util.file.messages.Message;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class MessageAction extends VoucherElement {

    private Message message;

    public MessageAction(MegaVouchersPlugin plugin, ConfigurationSection section) {
        super(plugin, section);

        ConfigurationSection messageSection = section.getConfigurationSection("message");

        if (messageSection == null) {
            messageSection = section.getConfigurationSection("messages");

            if (messageSection == null) {
                return;
            }
        }

        message = plugin.getMessages().getMessage(messageSection);
    }

    @Override
    public void execute(Player player) {
        if (message != null) {
            message.send(player);
        }
    }
}
