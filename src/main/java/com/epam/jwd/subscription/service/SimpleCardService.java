package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.CardDao;
import com.epam.jwd.subscription.entity.Card;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleCardService implements CardService {

    private final CardDao cardDao;
    private final ReentrantLock lock;

    public SimpleCardService(CardDao cardDao) {
        this.cardDao = cardDao;
        this.lock = new ReentrantLock();
    }

    @Override
    public List<Card> findAll() {
        return cardDao.read();
    }

    @Override
    public Optional<Card> findById(Long id) {
        return cardDao.read(id);
    }

    @Override
    public Optional<Card> create(Card entity) {
        return Optional.ofNullable(cardDao.create(entity));
    }

    @Override
    public boolean delete(Long id) {
        return cardDao.delete(id);
    }

    @Override
    public Optional<Card> readCardByNumber(String cardNumber) {
        return cardDao.readCardByNumber(cardNumber);
    }

    @Override
    @Transactional
    public void transferMoney(Card from, Card to, BigDecimal amount) {
        lock.lock();
        try {
            double amountFrom = from.getAmount().doubleValue();
            double amountTo = to.getAmount().doubleValue();
            amountFrom -= amount.doubleValue();
            amountTo += amount.doubleValue();
            cardDao.updateAmount(BigDecimal.valueOf(amountFrom), from.getCardNumber());
            cardDao.updateAmount(BigDecimal.valueOf(amountTo), to.getCardNumber());
        } finally {
            lock.unlock();
        }
    }
}
