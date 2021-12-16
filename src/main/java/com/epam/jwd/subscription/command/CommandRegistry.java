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
    SHOW_ADDRESS_DATA(ShowAddressDataCommand.getInstance(), "show_address", USER),
    SHOW_TERM_DATA(ShowTermDataCommand.getInstance(),"show_term", USER),
    SHOW_SUBSCRIPTION_DATA(ShowSubscriptionDataCommand.getInstance(), "show_subscription", USER),
    ADD_TO_SHOPPING_CARD(AddToShoppingCardCommand.getInstance(), "add_to_shopping_card", USER),
    SHOPPING_CARD(ShowShoppingCardCommand.getInstance(), "show_shopping_card", USER),
    DELETE_FROM_SHOPPING_CARD(DeleteFromShoppingCardCommand.getInstance(), "delete_from_shopping_card", USER),
    SHOW_CONFIRMATION_PAYMENT(ShowConfirmPaymentCommand.getInstance(), "confirmation", USER),
    CHANGE_LANGUAGE(ChangeLanguageCommand.getInstance(), "change_language"),
    DELETE_EDITION(DeleteEditionCommand.getInstance(), "delete_edition", ADMIN),
    DELETE_ACCOUNT(DeleteAccountCommand.getInstance(), "delete_account", ADMIN),
    SHOW_UPDATE_EDITION(ShowUpdateEditionPageCommand.getInstance(), "show_update_edition", ADMIN),
    UPDATE_EDITION(UpdateEditionCommand.getInstance(), "update_edition", ADMIN),
    SHOW_ADD_EDITION(ShowAddEditionPageCommand.getInstance(), "show_add_edition", ADMIN),
    ADD_EDITION(AddEditionCommand.getInstance(), "add_edition", ADMIN),
    SHOW_ARCHIVE(ShowArchiveCommand.getInstance(), "show_archive", ADMIN),
    SHOW_MY_ARCHIVE(ShowMySubscriptionsCommand.getInstance(), "show_my_archive", USER),
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
