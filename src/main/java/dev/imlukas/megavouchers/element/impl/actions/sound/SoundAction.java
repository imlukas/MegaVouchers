package dev.imlukas.megavouchers.element.impl.actions.sound;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.VoucherElement;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SoundAction extends VoucherElement {

    private final List<ActionSound> sounds = new ArrayList<>();

    public SoundAction(MegaVouchersPlugin plugin, ConfigurationSection section) {
        super(plugin, section);

        for (String soundSection : section.getKeys(false)) {

            ConfigurationSection soundConfig = section.getConfigurationSection(soundSection);

            Sound sound = Sound.valueOf(soundConfig.getString("sound").toUpperCase(Locale.ROOT));
            double volume = soundConfig.getDouble("volume");
            double pitch = soundConfig.getDouble("pitch");

            sounds.add(new ActionSound(sound, volume, pitch));
        }
    }

    @Override
    public void execute(Player player) {
        sounds.forEach(sound -> sound.play(player));
    }
}
