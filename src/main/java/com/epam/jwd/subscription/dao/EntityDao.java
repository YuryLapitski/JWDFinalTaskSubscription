package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface EntityDao<T extends Entity> {

    T create(T entity);

    List<T> read();

    Optional<T> read(Long id);

    T update(T entity);

    boolean delete(Long id);

}
