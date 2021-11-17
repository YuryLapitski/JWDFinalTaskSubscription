package com.epam.jwd.subscription.entity;

import java.util.Objects;

import static com.epam.jwd.subscription.entity.Role.USER;

public class Account implements Entity {

    private static final long serialVersionUID = -8575163775068072134L;
    private static final Integer USER_ROLE_ID = 1;
    private Long accId;
    private String login;
    private String password;
    private Integer roleId;
    private Role role;

    public Account(Long accId, String login, String password, Integer roleId, Role role) {
        this.accId = accId;
        this.login = login;
        this.password = password;
        this.roleId = roleId;
        this.role = role;
    }

    public Account(Long accId, String login, String password) {
        this(accId, login, password, USER_ROLE_ID, USER);
    }

    public Account(String login, String password, Integer roleId) {
        this.login = login;
        this.password = password;
        this.roleId = roleId;
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

    public Integer getRoleId() {
        return roleId;
    }

    public Role getRole() {
        return role;
    }

    public Account withPassword(String password) {
        return new Account(accId, login, password);
    }

    private Account() {

    }

    public static class Builder {
        private final Account account;

        public Builder() {
            account = new Account();
        }

        public Builder withId(Long id) {
            account.accId = id;
            return this;
        }

        public Builder withLogin(String login) {
            account.login = login;
            return this;
        }

        public Builder withPassword(String password) {
            account.password = password;
            return this;
        }

        public Builder withRoleId(Integer roleId) {
            account.roleId = roleId;
            return this;
        }

        public Account build() {
            return account;
        }
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
                ", roleId=" + roleId +
                ", role=" + role +
                '}';
    }
}
