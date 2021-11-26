package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.entity.Role;

import java.util.Arrays;
import java.util.List;

import static com.epam.jwd.subscription.entity.Role.*;

public enum CommandRegistry {

    MAIN_PAGE(ShowMainPageCommand.getInstance(),"main_page"),
    SHOW_LOGIN(ShowLoginPageCommand.getInstance(),"show_login", UNAUTHORIZED),
    LOGIN(LoginCommand.getInstance(), "login", UNAUTHORIZED),
    LOGOUT(LogoutCommand.getInstance(), "logout", USER, ADMIN),
    SHOW_SIGNUP(ShowSignUpPageCommand.getInstance(), "show_signup", UNAUTHORIZED),
    SIGNUP(SignUpCommand.getInstance(), "signup", UNAUTHORIZED),
    SHOW_ACCOUNTS(ShowAccountsPageCommand.getInstance(), "show_accounts", ADMIN),
    SHOW_EDITIONS(ShowEditionsPageCommand.getInstance(), "show_editions"),
    SHOW_USERS(ShowUsersPageCommand.getInstance(), "show_users", ADMIN),
    SHOW_USER_DATA(ShowUserDataPageCommand.getInstance(), "show_user_data", USER),
    USER_DATA_SUBMIT(UserDataSubmitCommand.getInstance(), "user_data", USER),
    CHOOSE_EDITION(ChooseEditionCommand.getInstance(), "choose_edition", USER),
    ERROR(ShowErrorPageCommand.getInstance(), "show_error"),
    DEFAULT(ShowMainPageCommand.getInstance(), "");

    private final Command command;
    private final String path;
    private final List<Role> allowedRoles;

    CommandRegistry(Command command, String path, Role... roles) {
        this.command = command;
        this.path = path;
        this.allowedRoles = roles != null && roles.length > 0 ? Arrays.asList(roles) : Role.valuesAsList();
    }

    public Command getCommand() {
        return command;
    }

    public List<Role> getAllowedRoles() {
        return allowedRoles;
    }

    static Command of(String name) {
        for (CommandRegistry constant : values()){
            if (constant.path.equalsIgnoreCase(name)) {
                return constant.command;
            }
        }
        return DEFAULT.command;
    }
}
