package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.entity.Address;

import java.util.Optional;

public interface AddressDao extends EntityDao<Address> {

    Address create(Address address);

    Optional<Address> selectByCSHF(String city, String street, String house, Integer flat);

    static AddressDao instance() {
        return MethodAddressDao.getInstance();
    }
}
