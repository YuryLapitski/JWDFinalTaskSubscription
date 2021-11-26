package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.PriceDao;
import com.epam.jwd.subscription.entity.Price;

import java.util.List;
import java.util.Optional;

public class PriceService implements EntityService<Price> {

    private final PriceDao priceDao;

    public PriceService(PriceDao priceDao) {
        this.priceDao = priceDao;
    }

    @Override
    public List<Price> findAll() {
        return null;
    }

    @Override
    public Optional<Price> create(Price entity) {
        return Optional.empty();
    }

    @Override
    public List<Price> findAllById(String id) {
        return priceDao.read();
    }
}
