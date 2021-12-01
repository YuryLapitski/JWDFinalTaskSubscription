package com.epam.jwd.subscription.entity;

import java.util.Objects;

public class Address implements Entity {

    private static final long serialVersionUID = 5118385619564662009L;
    private final Long id;
    private final String city;
    private final String street;
    private final String house;
    private final Integer flat;

    public Address(Long id, String city, String street, String house, Integer flat) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
    }

    public Address(String city, String street, String house, Integer flat) {
        this(null, city, street, house, flat);
    }

    @Override
    public Long getId() {
        return null;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) &&
                Objects.equals(city, address.city) &&
                Objects.equals(street, address.street) &&
                Objects.equals(house, address.house) &&
                Objects.equals(flat, address.flat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, street, house, flat);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house=" + house +
                ", flat=" + flat +
                '}';
    }
}
