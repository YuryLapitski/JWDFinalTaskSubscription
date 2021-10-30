package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.entity.Entity;

import java.util.List;

public interface EntityService<T extends Entity> {

    List<T> findAll();

}
