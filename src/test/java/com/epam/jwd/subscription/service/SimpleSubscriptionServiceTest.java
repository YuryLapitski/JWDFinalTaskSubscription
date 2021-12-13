package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.MethodSubscriptionDao;
import com.epam.jwd.subscription.entity.Subscription;
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
class SimpleSubscriptionServiceTest {

    private MethodSubscriptionDao dao;
    private Subscription subscription;
    private Long id;
    private Long userId;
    private Long addressId;
    private Long editionId;
    private Long termId;
    private Long priceId;
    private Long statusId;
    private List<Subscription> subscriptionList;

    @BeforeAll
    void beforeAll(){
        dao = mock(MethodSubscriptionDao.class);
        subscription = new Subscription(1L, 1L, 1L, 1L, 1L, 1L, 1L);
        id = 1L;
        userId = 1L;
        addressId = 1L;
        editionId = 1L;
        termId = 1L;
        priceId = 1L;
        statusId = 1L;
        subscriptionList = new ArrayList<>();
        subscriptionList.add(subscription);
    }

    @Test
    void findAll() {
        List<Subscription> expectedResult = subscriptionList;
        when(dao.read()).thenReturn(expectedResult);
        List<Subscription> actualResult = dao.read();
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void findById() {
        Optional<Subscription> expectedResult = Optional.of(subscription);
        when(dao.read(id)).thenReturn(expectedResult);
        Optional<Subscription> actualResult = dao.read(id);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void create() {
        Subscription expectedResult = subscription;
        when(dao.create(subscription)).thenReturn(expectedResult);
        Subscription actualResult = dao.create(subscription);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void delete() {
        when(dao.delete(id)).thenReturn(true);
        boolean actualResult = dao.delete(id);
        assertTrue(actualResult);
    }

    @Test
    void findIdByAll() {
        List<Subscription> expectedResult = subscriptionList;
        when(dao.findIdByAll(userId, addressId, editionId, termId, priceId, statusId)).thenReturn(expectedResult);
        List<Subscription> actualResult = dao.findIdByAll(userId, addressId, editionId, termId, priceId, statusId);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void findByEditionId() {
        List<Subscription> expectedResult = subscriptionList;
        when(dao.findByEditionId(editionId)).thenReturn(expectedResult);
        List<Subscription> actualResult = dao.findByEditionId(editionId);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void findByUserId() {
        List<Subscription> expectedResult = subscriptionList;
        when(dao.findByUserId(userId)).thenReturn(expectedResult);
        List<Subscription> actualResult = dao.findByUserId(userId);
        assertEquals(actualResult, expectedResult);
    }
}