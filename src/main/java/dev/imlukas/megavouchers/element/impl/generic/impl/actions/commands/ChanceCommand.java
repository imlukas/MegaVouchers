package dev.imlukas.megavouchers.element.impl.generic.impl.actions.commands;

public class ChanceCommand extends NormalCommand {

    private final double chance;

    public ChanceCommand(String command, double chance) {
        super(command);
        this.chance = chance;
    }

    public double getChance() {
        return chance;
    }
}
