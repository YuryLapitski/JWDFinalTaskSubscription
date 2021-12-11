package com.epam.jwd.subscription.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class Archive implements Entity {

    private static final long serialVersionUID = 2905776823841690810L;
    private final Long id;
    private final Long accId;
    private final String editionName;
    private final Integer term;
    private final BigDecimal price;
    private final String city;
    private final String street;
    private final String house;
    private final Integer flat;
    private final String statusName;
    private final Timestamp date;

    public Archive(Long id, Long accId, String editionName, Integer term, BigDecimal price,
                   String city, String street, String house, Integer flat, String statusName, Timestamp date) {
        this.id = id;
        this.accId = accId;
        this.editionName = editionName;
        this.term = term;
        this.price = price;
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.statusName = statusName;
        this.date = date;
    }

    public Archive(Long accId, String editionName, Integer term, BigDecimal price,
                   String city, String street, String house, Integer flat, String statusName) {
        this(null, accId, editionName, term, price, city, street, house, flat, statusName, null);
    }

    @Override
    public Long getId() {
        return id;
    }

    public Long getAccId() {
        return accId;
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

    public String getStatusName() {
        return statusName;
    }

    public Timestamp getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Archive archive = (Archive) o;
        return Objects.equals(id, archive.id) &&
                Objects.equals(accId, archive.accId) &&
                Objects.equals(editionName, archive.editionName) &&
                Objects.equals(term, archive.term) &&
                Objects.equals(price, archive.price) &&
                Objects.equals(city, archive.city) &&
                Objects.equals(street, archive.street) &&
                Objects.equals(house, archive.house) &&
                Objects.equals(flat, archive.flat) &&
                Objects.equals(statusName, archive.statusName) &&
                Objects.equals(date, archive.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accId, editionName, term, price, city, street, house, flat, statusName, date);
    }

    @Override
    public String toString() {
        return "Archive{" +
                "id=" + id +
                ", accId=" + accId +
                ", editionName='" + editionName + '\'' +
                ", term=" + term +
                ", price=" + price +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", flat=" + flat +
                ", statusName='" + statusName + '\'' +
                ", date=" + date +
                '}';
    }
}
