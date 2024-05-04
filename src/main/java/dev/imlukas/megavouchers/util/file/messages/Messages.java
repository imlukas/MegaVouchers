package dev.imlukas.megavouchers.util.file.messages;

import dev.imlukas.megavouchers.util.commands.audience.CommandAudience;
import dev.imlukas.megavouchers.util.file.YMLBase;
import dev.imlukas.megavouchers.util.file.messages.impl.ChatMessage;
import dev.imlukas.megavouchers.util.file.messages.impl.RawMessage;
import dev.imlukas.megavouchers.util.file.messages.provider.MessageProviderRegistry;
import dev.imlukas.megavouchers.util.text.TextUtils;
import dev.imlukas.megavouchers.util.text.placeholder.Placeholder;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

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
    public Messages(JavaPlugin plugin) {
        super(plugin, new File(plugin.getDataFolder(), "messages.yml"), true);
    }

    public final void send(CommandAudience audience, String path) {
        if (!getConfiguration().contains(path)) {
            return;
        }

        String msg = getConfiguration().getString(path);

        if (msg == null || msg.isEmpty()) {
            return;
        }

        audience.sendMessage(msg);
    }

    public final void send(Audience sender, String path) {
        send(sender, path, new Placeholder[0]);
    }

    @SafeVarargs
    public final void send(Audience sender, String path, Placeholder<Audience>... placeholders) {
        Message message = getMessage(path, placeholders);

        if (message == null) {
            return;
        }

        message.send(sender);
    }

    @SafeVarargs
    public final RawMessage getRawMessage(String path, Placeholder<Audience>... placeholders) {
        String rawMessage = getConfiguration().getString("messages." + path);

        if (rawMessage == null) {
            return null;
        }

        Component message = TextUtils.color(rawMessage);
        return new RawMessage(message, placeholders);
    }

    @SafeVarargs
    public final Message getMessage(ConfigurationSection section, Placeholder<Audience>... placeholders) {
        boolean enabled = section.getBoolean("enabled", true);

        if (!enabled) {
            return null;
        }

        if (!section.contains("type")) {
            return new ChatMessage(section, placeholders);
        }

        String type = section.getString("type");
        return registry.get(type, section, placeholders);
    }

    @SafeVarargs
    public final Message getMessage(String path, Placeholder<Audience>... placeholders) {
        ConfigurationSection section = getConfiguration().getConfigurationSection("messages." + path);

        if (section == null) {
            return getRawMessage(path, placeholders);
        }

        return getMessage(section, placeholders);
    }
}
