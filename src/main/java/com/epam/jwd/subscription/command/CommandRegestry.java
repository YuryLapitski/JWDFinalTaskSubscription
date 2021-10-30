package com.epam.jwd.subscription.command;

public enum CommandRegestry {

    MAIN_PAGE(ShowMainPageCommand.getInstance()),
    DEFAULT(ShowMainPageCommand.getInstance());

    private final Command command;

    CommandRegestry(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    static Command of(String name) {
        for (CommandRegestry constant : values()){
            if (constant.name().equals(name)) {
                return constant.command;
            }
        }
        return DEFAULT.command;
    }
}
