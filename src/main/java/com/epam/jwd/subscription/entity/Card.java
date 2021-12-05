package com.epam.jwd.subscription.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Card implements Entity {

    private static final long serialVersionUID = -839305628852974700L;
    private final Long id;
    private final String cardName;
    private final String cardNumber;
    private final BigDecimal amount;

    public Card(Long id, String cardName, String cardNumber, BigDecimal amount) {
        this.id = id;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.amount = amount;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id) &&
                Objects.equals(cardName, card.cardName) &&
                Objects.equals(cardNumber, card.cardNumber) &&
                Objects.equals(amount, card.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardName, cardNumber, amount);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardName='" + cardName + '\'' +
                ", cardNumber=" + cardNumber +
                ", amount=" + amount +
                '}';
    }
}
