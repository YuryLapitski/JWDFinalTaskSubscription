package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.entity.Card;

import java.util.Optional;

public interface CardService extends EntityService<Card> {

    Optional<Card> readCardByNumber(String cardNumber);

    boolean transferMoney (Card from, Card to, double amount);
}
