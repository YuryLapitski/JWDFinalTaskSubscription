package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.entity.Address;

import java.util.Optional;

public interface AddressService extends EntityService<Address> {

    Optional<Address> findByCSHF(String city, String street, String house, Integer flat);

}
