package com.epam.jwd.subscription.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Price implements Entity {

    private static final long serialVersionUID = 7313880770846891551L;
    private final Long id;
    private final Long editionId;
    private final Integer termId;
    private final BigDecimal value;

    public Price(Long id, Long editionId, Integer termId, BigDecimal value) {
        this.id = id;
        this.editionId = editionId;
        this.termId = termId;
        this.value = value;
    }

    public Price(Long editionId, Integer termId, BigDecimal value) {
        this(null, editionId, termId, value);
    }

    public Long getEditionId() {
        return editionId;
    }

    public Integer getTermId() {
        return termId;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(id, price.id) &&
                Objects.equals(editionId, price.editionId) &&
                Objects.equals(termId, price.termId) &&
                Objects.equals(value, price.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, editionId, termId, value);
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", editionId=" + editionId +
                ", termId=" + termId +
                ", value=" + value +
                '}';
    }
}
