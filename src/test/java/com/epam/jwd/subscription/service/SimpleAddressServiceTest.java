package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.MethodAccountDao;
import com.epam.jwd.subscription.dao.MethodAddressDao;
import com.epam.jwd.subscription.entity.Account;
import com.epam.jwd.subscription.entity.Address;
import com.epam.jwd.subscription.entity.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SimpleAddressServiceTest {

    private MethodAddressDao dao;
    private Address address;
    private Long id;
    private String city;
    private String street;
    private String house;
    private Integer flat;
    private List<Address> addressList;

    @BeforeAll
    void beforeAll(){
        dao = mock(MethodAddressDao.class);
        address = new Address(1L,"Minsk", "ul. Gromova", "53", 32);
        id = 1L;
        city = "Minsk";
        street = "ul. Gromova";
        house = "53";
        flat = 32;
        addressList = new ArrayList<>();
        addressList.add(address);
    }

    @Test
    void findByCSHF() {
        Optional<Address> expectedResult = Optional.of(address);
        when(dao.selectByCSHF(city, street, house, flat)).thenReturn(expectedResult);
        Optional<Address> actualResult = dao.selectByCSHF(city, street, house, flat);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void findAll() {
        List<Address> expectedResult = addressList;
        when(dao.read()).thenReturn(expectedResult);
        List<Address> actualResult = dao.read();
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void findById() {
        Optional<Address> expectedResult = Optional.of(address);
        when(dao.read(id)).thenReturn(expectedResult);
        Optional<Address> actualResult = dao.read(id);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void create() {
        Address expectedResult = address;
        when(dao.create(address)).thenReturn(expectedResult);
        Address actualResult = dao.create(address);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void delete() {
        when(dao.delete(id)).thenReturn(true);
        boolean actualResult = dao.delete(id);
        assertTrue(actualResult);
    }
}