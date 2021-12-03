package com.epam.jwd.subscription.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class SubscrShow implements Serializable {

    private static final long serialVersionUID = -710044745596233749L;
    private final Long subscrId;
    private final String firstName;
    private final String lastName;
    private final String city;
    private final String street;
    private final String house;
    private final Integer flat;
    private final String editionName;
    private final Integer term;
    private final BigDecimal price;
    private final String status;

    public SubscrShow(Long subscrId, String firstName, String lastName, String city, String street, String house,
                      Integer flat, String editionName, Integer term, BigDecimal price, String status) {
        this.subscrId = subscrId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.editionName = editionName;
        this.term = term;
        this.price = price;
        this.status = status;
    }

    public Long getSubscrId() {
        return subscrId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public Integer getFlat() {
        return flat;
    }

    public String getEditionName() {
        return editionName;
    }

    public Integer getTerm() {
        return term;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscrShow that = (SubscrShow) o;
        return Objects.equals(subscrId, that.subscrId) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(city, that.city) &&
                Objects.equals(street, that.street) &&
                Objects.equals(house, that.house) &&
                Objects.equals(flat, that.flat) &&
                Objects.equals(editionName, that.editionName) &&
                Objects.equals(term, that.term) &&
                Objects.equals(price, that.price) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscrId, firstName, lastName, city, street, house,
                flat, editionName, term, price, status);
    }

    @Override
    public String toString() {
        return "SubscrShow{" +
                "id=" + subscrId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", flat=" + flat +
                ", editionName='" + editionName + '\'' +
                ", term='" + term + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}
