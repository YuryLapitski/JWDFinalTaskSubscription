package com.epam.jwd.subscription.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Edition implements Entity {

    private static final long serialVersionUID = 8358477032277908841L;
    private final Long id;
    private final String name;
    private final Category category;
    private final BigDecimal threeMonthsPrice;
    private final BigDecimal sixMonthsPrice;
    private final BigDecimal twelveMonthsPrice;


    public Edition(Long id, String name, Category category,
                   BigDecimal threeMonthsPrice, BigDecimal sixMonthsPrice,
                   BigDecimal twelveMonthsPrice) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.threeMonthsPrice = threeMonthsPrice;
        this.sixMonthsPrice = sixMonthsPrice;
        this.twelveMonthsPrice = twelveMonthsPrice;
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

    public BigDecimal getThreeMonthsPrice() {
        return threeMonthsPrice;
    }

    public BigDecimal getSixMonthsPrice() {
        return sixMonthsPrice;
    }

    public BigDecimal getTwelveMonthsPrice() {
        return twelveMonthsPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edition edition = (Edition) o;
        return Objects.equals(id, edition.id) &&
                Objects.equals(name, edition.name) &&
                category == edition.category &&
                Objects.equals(threeMonthsPrice, edition.threeMonthsPrice) &&
                Objects.equals(sixMonthsPrice, edition.sixMonthsPrice) &&
                Objects.equals(twelveMonthsPrice, edition.twelveMonthsPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, threeMonthsPrice, sixMonthsPrice, twelveMonthsPrice);
    }

    @Override
    public String toString() {
        return "Edition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", threeMonthsPrice=" + threeMonthsPrice +
                ", sixMonthsPrice=" + sixMonthsPrice +
                ", twelveMonthsPrice=" + twelveMonthsPrice +
                '}';
    }
}
