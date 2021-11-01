package com.epam.jwd.subscription.command;

public enum CommandRegestry {

    MAIN_PAGE(ShowMainPageCommand.getInstance(),"main_page"),
    SHOW_ACCOUNTS(ShowAccountsPageCommand.getInstance(), "show_accounts"),
    SHOW_EDITIONS(ShowEditionsPageCommand.getInstance(), "show_editions"),
    SHOW_USERS(ShowUsersPageCommand.getInstance(), "show_users"),
    DEFAULT(ShowMainPageCommand.getInstance(), "");

    private final Command command;
    private final String path;

    CommandRegestry(Command command, String path) {
        this.command = command;
        this.path = path;
    }

    public Command getCommand() {
        return command;
    }

    static Command of(String name) {
        for (CommandRegestry constant : values()){
            if (constant.path.equalsIgnoreCase(name)) {
                return constant.command;
            }
        }
        return DEFAULT.command;
    }
}
