package dev.imlukas.megavouchers.element.impl.actions.sound;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ActionSound {

    private final Sound sound;
    private final double volume;
    private final double pitch;

    public ActionSound(Sound sound, double volume, double pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public void play(Player player) {
        player.playSound(player.getLocation(), sound, (float) volume, (float) pitch);
    }
}
