package dev.imlukas.megavouchers.util.file.messages;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.util.file.YMLBase;
import dev.imlukas.megavouchers.util.file.messages.impl.ChatMessage;
import dev.imlukas.megavouchers.util.file.messages.impl.RawMessage;
import dev.imlukas.megavouchers.util.file.messages.provider.MessageProviderRegistry;
import dev.imlukas.megavouchers.util.text.ComponentPlaceholder;
import dev.imlukas.megavouchers.util.text.Placeholder;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.ConfigurationSection;

/**
 * This class is responsible for handling the loading and saving of the messages.yml file. It also provides a method for sending messages to players.
 */
public class Messages extends YMLBase {

    private final MessageProviderRegistry registry = new MessageProviderRegistry();

    /**
     * Creates a new messages file.
     *
     * @param plugin The platform this messages file is for.
     */
    public Messages(MegaVouchersPlugin plugin) {
        super(plugin, "messages.yml");
    }

    @SafeVarargs
    public final void send(Audience sender, String path, Placeholder<Audience>... placeholders) {
        ComponentPlaceholder<Audience>[] componentPlaceholders = new ComponentPlaceholder[placeholders.length];

        for (int i = 0; i < placeholders.length; i++) {
            Placeholder<Audience> placeholder = placeholders[i];
            componentPlaceholders[i] = placeholder.asComponentPlaceholder();
        }

        send(sender, path, componentPlaceholders);
    }

    @SafeVarargs
    public final void send(Audience sender, String path, ComponentPlaceholder<Audience>... placeholders) {
        Message message = getMessage(path);

        if (message == null) {
            return;
        }

        message.send(sender);
    }

    @SafeVarargs
    public final RawMessage getRawMessage(String path, ComponentPlaceholder<Audience>... placeholders) {
        String rawMessage = getConfiguration().getString("messages." + path);

        if (rawMessage == null) {
            return null;
        }

        Component message = MiniMessage.miniMessage().deserialize(rawMessage);
        return new RawMessage(message, placeholders);
    }

    @SafeVarargs
    public final Message getMessage(String path, ComponentPlaceholder<Audience>... placeholders) {
        ConfigurationSection section = getConfiguration().getConfigurationSection("messages." + path);
        if (section == null) {
            return getRawMessage(path, placeholders);
        }

        if (!section.contains("type")) {
            return new ChatMessage(section, placeholders);
        }

        boolean enabled = section.getBoolean("enabled", true);

        if (!enabled) {
            return null;
        }

        String type = section.getString("type");
        return registry.get(type, section, placeholders);
    }
}
