package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.entity.Account;
import com.epam.jwd.subscription.entity.Price;

import java.util.List;
import java.util.Optional;

public interface PriceDao extends EntityDao<Price> {

    List<Price> findPricesByEditionId(Long editionId);

    static PriceDao instance() {
        return MethodPriceDao.getInstance();
    }
}
