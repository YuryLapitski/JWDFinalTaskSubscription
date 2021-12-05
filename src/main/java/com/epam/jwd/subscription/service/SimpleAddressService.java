package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.AddressDao;
import com.epam.jwd.subscription.entity.Address;

import java.util.List;
import java.util.Optional;

public class SimpleAddressService implements AddressService {

    private final AddressDao addressDao;

    public SimpleAddressService(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public Optional<Address> findByCSHF(String city, String street, String house, Integer flat) {
        return addressDao.selectByCSHF(city, street, house, flat);
    }

    @Override
    public List<Address> findAll() {
        return null;
    }

    @Override
    public Optional<Address> findById(Long id) {
        return addressDao.read(id);
    }

    @Override
    public Optional<Address> create(Address entity) {
        return Optional.ofNullable(addressDao.create(entity));
    }
}
