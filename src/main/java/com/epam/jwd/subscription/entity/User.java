package com.epam.jwd.subscription.entity;

import java.util.Objects;

public class User implements Entity {

    private static final long serialVersionUID = 1343428821527920272L;
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final String email;
    private final Long accId;

    public User(Long id, String firstName, String lastName, int age, String email, Long accId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.accId = accId;
    }

    public User(String firstName, String lastName, int age, String email, Long accId) {
        this(null, firstName, lastName, age, email, accId);
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public Long getAccId() {
        return accId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age &&
                Objects.equals(id, user.id) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(accId, user.accId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, age, email, accId);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", accId=" + accId +
                '}';
    }
}
