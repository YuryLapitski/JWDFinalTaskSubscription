package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.MethodPriceDao;
import com.epam.jwd.subscription.entity.Price;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SimplePriceServiceTest {

    private MethodPriceDao dao;
    private Price price;
    private Long id;
    private Long edId;
    private Long termId;
    private String edName;
    private List<Price> priceList;

    @BeforeAll
    void beforeAll(){
        dao = mock(MethodPriceDao.class);
        price = new Price(1L, 1L, 1L, BigDecimal.valueOf(30.00));
        id = 1L;
        edId = 1L;
        termId = 1L;
        priceList = new ArrayList<>();
        priceList.add(price);
    }

    @Test
    void findAll() {
        List<Price> expectedResult = priceList;
        when(dao.read()).thenReturn(expectedResult);
        List<Price> actualResult = dao.read();
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void create() {
        Price expectedResult = price;
        when(dao.create(price)).thenReturn(expectedResult);
        Price actualResult = dao.create(price);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void delete() {
        when(dao.delete(id)).thenReturn(true);
        boolean actualResult = dao.delete(id);
        assertTrue(actualResult);
    }

    @Test
    void findById() {
        Optional<Price> expectedResult = Optional.of(price);
        when(dao.read(id)).thenReturn(expectedResult);
        Optional<Price> actualResult = dao.read(id);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void findByEditionIdTermID() {
        Optional<Price> expectedResult = Optional.of(price);
        when(dao.findByEditionIdTermID(edId, termId)).thenReturn(expectedResult);
        Optional<Price> actualResult = dao.findByEditionIdTermID(edId, termId);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void findPricesByEditionId() {
        List<Price> expectedResult = priceList;
        when(dao.findPricesByEditionId(edId)).thenReturn(expectedResult);
        List<Price> actualResult = dao.findPricesByEditionId(edId);
        assertEquals(actualResult, expectedResult);
    }
}