package com.epam.jwd.subscription.command;

public interface Command {

    CommandResponce execute (CommandRequest request);

    static Command of(String name) {
        return CommandRegestry.of(name);
    }

}
