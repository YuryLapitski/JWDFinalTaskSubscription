package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.PriceDao;
import com.epam.jwd.subscription.entity.Price;

import java.math.BigDecimal;
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
        return Optional.ofNullable(priceDao.create(entity));
    }

    @Override
    public boolean delete(Long id) {
        return priceDao.delete(id);
    }

    @Override
    public Optional<Price> findById(Long id) {
       return priceDao.read(id);
    }

    @Override
    public Optional<Price> findByEditionIdTermID(Long editionId, Long termId) {
        return priceDao.findByEditionIdTermID(editionId, termId);
    }

    @Override
    public List<Price> findPricesByEditionId(Long editionId) {
        return priceDao.findPricesByEditionId(editionId);
    }

    @Override
    public void updateValue(BigDecimal value, Long editionId, Long termId) {
        priceDao.updateValue(value, editionId, termId);
    }
}
