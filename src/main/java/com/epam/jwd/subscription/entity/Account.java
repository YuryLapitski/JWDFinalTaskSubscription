package com.epam.jwd.subscription.entity;

import java.util.Objects;

import static com.epam.jwd.subscription.entity.Role.USER;

public class Account implements Entity {

    private static final long serialVersionUID = -8575163775068072134L;
    private final Long accId;
    private final String login;
    private final String password;
    private final Role role;

    public Account(Long accId, String login, String password, Role role) {
        this.accId = accId;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Account(Long accId, String login, String password) {
        this(accId, login, password, USER);
    }

    @Override
    public Long getId() {
        return accId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public Account withPassword(String password) {
        return new Account(accId, login, password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accId, account.accId) &&
                Objects.equals(login, account.login) &&
                Objects.equals(password, account.password) &&
                role == account.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accId, login, password, role);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accId=" + accId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
