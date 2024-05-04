package dev.imlukas.megavouchers.element.impl.generic.impl.actions.commands;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.VoucherElement;
import dev.imlukas.megavouchers.util.collection.ListUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomCommand extends VoucherElement {

    private final Pattern pattern = Pattern.compile("\\[(.*)]");
    private final int commandAmount;
    private final List<String> commands = new ArrayList<>();

    public RandomCommand(MegaVouchersPlugin plugin, ConfigurationSection section) {
        super(plugin, section);
        commandAmount = section.getInt("commandAmount");
        commands.addAll(section.getStringList("commands"));
    }

    @Override
    public void execute(Player player) {


        if (commandAmount > commands.size()) {
            player.sendMessage("§cThe amount of commands is less than the commandAmount");
            return;
        }

        for (int i = 0; i < commandAmount; i++) {
            String command = ListUtils.getRandom(commands);

            if (command == null) {
                continue;
            }

            Matcher matcher = pattern.matcher(command);

            if (!matcher.find()) {
                player.performCommand(command);
                return;
            }

            String commandSender = matcher.group(1);
            String toExecute = command.replace("[" + commandSender + "] ", "").replace("%player%", player.getName());

            if (commandSender.equalsIgnoreCase("console")) {
                player.getServer().dispatchCommand(Bukkit.getConsoleSender(), toExecute);
            } else {
                player.performCommand(toExecute);
            }
        }
    }
}
