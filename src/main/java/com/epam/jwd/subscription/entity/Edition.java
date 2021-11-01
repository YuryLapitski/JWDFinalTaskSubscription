package com.epam.jwd.subscription.entity;

import java.util.Objects;

public class Edition implements Entity {

    private static final long serialVersionUID = 5332899225382136918L;
    private final Long id;
    private final String name;
    private final Category category;

    public Edition(Long id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edition edition = (Edition) o;
        return Objects.equals(id, edition.id) &&
                Objects.equals(name, edition.name) &&
                Objects.equals(category, edition.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category);
    }

    @Override
    public String toString() {
        return "Edition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                '}';
    }
}
