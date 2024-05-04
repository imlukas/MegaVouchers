package dev.imlukas.megavouchers.element.impl.generic.impl.rewards;

import dev.imlukas.megavouchers.MegaVouchersPlugin;
import dev.imlukas.megavouchers.element.VoucherElement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class HpReward extends VoucherElement {

    private final int hp;

    protected HpReward(MegaVouchersPlugin plugin, ConfigurationSection section) {
        super(plugin, section);

        this.hp = section.getInt("value", 0);
    }

    @Override
    public void execute(Player player) {
        player.setHealth(player.getHealth() + hp);
    }
}
