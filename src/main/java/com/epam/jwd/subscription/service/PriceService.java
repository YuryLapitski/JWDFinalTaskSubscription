package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.entity.Price;

import java.math.BigDecimal;
import java.util.Optional;

public interface PriceService extends EntityService<Price> {

    Optional<Price> findByEditionIdTermID(Long editionId, Long termId);

    void updateValue(BigDecimal value, Long editionId, Long termId);

}
