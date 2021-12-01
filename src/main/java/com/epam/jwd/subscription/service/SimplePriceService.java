package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.PriceDao;
import com.epam.jwd.subscription.entity.Price;

import java.util.List;
import java.util.Optional;

public class SimplePriceService implements PriceService {

    private final PriceDao priceDao;

    public SimplePriceService(PriceDao priceDao) {
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
    public Optional<Price> findById(Long id) {
       return priceDao.read(id);
    }

    @Override
    public Optional<Price> findByEditionIdTermID(Long editionId, Long termId) {
        return priceDao.findByEditionIdTermID(editionId, termId);
    }
}
