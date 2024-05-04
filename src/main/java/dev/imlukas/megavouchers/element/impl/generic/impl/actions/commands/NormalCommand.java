package dev.imlukas.megavouchers.element.impl.generic.impl.actions.commands;

public class NormalCommand implements Command {

    private final String command;

    public NormalCommand(String command) {
        this.command = command;
    }

    @Override
    public String getCommand() {
        return command;
    }
}
