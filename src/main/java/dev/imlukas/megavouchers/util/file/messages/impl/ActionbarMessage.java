package dev.imlukas.megavouchers.util.file.messages.impl;

import dev.imlukas.megavouchers.util.file.messages.AbstractMessage;
import dev.imlukas.megavouchers.util.text.ComponentPlaceholder;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.ConfigurationSection;


public class ActionbarMessage extends AbstractMessage {

    private final Component message;

    @SafeVarargs
    public ActionbarMessage(ConfigurationSection section, ComponentPlaceholder<Audience>... placeholders) {
        super(section, placeholders);
        message = MiniMessage.miniMessage().deserialize(section.getString("message"));
    }

    @Override
    public void send(Audience audience) {
        audience.sendActionBar(replacePlaceholders(message, audience));
    }
}
