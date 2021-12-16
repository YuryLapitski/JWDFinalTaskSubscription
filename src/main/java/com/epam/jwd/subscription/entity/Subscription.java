package com.epam.jwd.subscription.entity;

import java.sql.Timestamp;
import java.util.Objects;

public class Subscription implements Entity {

    private static final long serialVersionUID = 8389683691097272100L;
    private final Long id;
    private final Long userId;
    private final Long addressId;
    private final Long editionId;
    private final Long termId;
    private final Long priceId;
    private final Long statusId;
    private final Timestamp timestamp;

    public Subscription(Long id, Long userId, Long addressId, Long editionId,
                        Long termId, Long priceId, Long statusId, Timestamp timestamp) {
        this.id = id;
        this.userId = userId;
        this.addressId = addressId;
        this.editionId = editionId;
        this.termId = termId;
        this.priceId = priceId;
        this.statusId = statusId;
        this.timestamp = timestamp;
    }

    public Subscription(Long userId, Long addressId, Long editionId,
                        Long termId, Long priceId, Long statusId) {
        this(null, userId, addressId, editionId, termId, priceId, statusId, null);
    }

    @Override
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public Long getEditionId() {
        return editionId;
    }

    public Long getTermId() {
        return termId;
    }

    public Long getPriceId() {
        return priceId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(addressId, that.addressId) &&
                Objects.equals(editionId, that.editionId) &&
                Objects.equals(termId, that.termId) &&
                Objects.equals(priceId, that.priceId) &&
                Objects.equals(statusId, that.statusId) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, addressId, editionId, termId, priceId, statusId, timestamp);
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", userId=" + userId +
                ", addressId=" + addressId +
                ", editionId=" + editionId +
                ", termId=" + termId +
                ", priceId=" + priceId +
                ", statusId=" + statusId +
                ", timestamp=" + timestamp +
                '}';
    }
}
