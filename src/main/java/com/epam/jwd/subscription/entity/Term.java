package com.epam.jwd.subscription.entity;

import java.util.Objects;

public class Term implements Entity {

    private static final long serialVersionUID = 3245611316538132503L;
    private final Long id;
    private final Integer months;

    public Term(Long id, Integer months) {
        this.id = id;
        this.months = months;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Integer getMonths() {
        return months;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Term term = (Term) o;
        return Objects.equals(id, term.id) &&
                Objects.equals(months, term.months);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, months);
    }

    @Override
    public String toString() {
        return "Term{" +
                "id=" + id +
                ", months=" + months +
                '}';
    }
}
