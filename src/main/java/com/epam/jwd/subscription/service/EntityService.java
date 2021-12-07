package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface EntityService<T extends Entity> {

    List<T> findAll();

    Optional<T> findById(Long id);

    Optional<T> create(T entity);

    boolean delete(Long id);

}
