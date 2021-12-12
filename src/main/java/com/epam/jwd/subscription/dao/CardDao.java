package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.entity.Card;

import java.math.BigDecimal;
import java.util.Optional;

public interface CardDao extends EntityDao<Card> {

    Optional<Card> readCardByNumber(String cardNumber);

    boolean updateAmount (BigDecimal amount, String cardNumber);

    static CardDao instance() {
        return MethodCardDao.getInstance();
    }
}
