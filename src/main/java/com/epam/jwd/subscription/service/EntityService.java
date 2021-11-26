package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.entity.Entity;
import com.epam.jwd.subscription.entity.Price;

import java.util.List;
import java.util.Optional;

public interface EntityService<T extends Entity> {

    List<T> findAll();

    List<T> findAllById(String id);

    Optional<T> create(T entity);

}
