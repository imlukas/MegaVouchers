package dev.imlukas.megavouchers.util.file.messages.provider;

import dev.imlukas.megavouchers.util.file.messages.Message;
import dev.imlukas.megavouchers.util.text.placeholder.Placeholder;
import net.kyori.adventure.audience.Audience;
import org.bukkit.configuration.ConfigurationSection;

public interface MessageProvider {
    Message provide(ConfigurationSection section, Placeholder<Audience>... placeholders);
}
