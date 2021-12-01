package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.entity.Price;

import java.util.List;
import java.util.Optional;

public interface PriceDao extends EntityDao<Price> {

    List<Price> findPricesByEditionId(Long editionId);

    Optional<Price> findByEditionIdTermID(Long editionId, Long termId);

    static PriceDao instance() {
        return MethodPriceDao.getInstance();
    }
}
