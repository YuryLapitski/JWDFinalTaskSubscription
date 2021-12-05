package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.entity.Card;

import java.math.BigDecimal;
import java.util.Optional;

public interface CardService extends EntityService<Card> {

    Optional<Card> readCardByNumber(String cardNumber);

    void transferMoney (Card from, Card to, BigDecimal amount);
}
