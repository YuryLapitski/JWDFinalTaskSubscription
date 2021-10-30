package com.epam.jwd.subscription.command;

public interface Command {

    CommandResponse execute (CommandRequest request);

    static Command of(String name) {
        return CommandRegestry.of(name);
    }

}
