package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.MethodStatusDao;
import com.epam.jwd.subscription.dao.MethodSubscriptionDao;
import com.epam.jwd.subscription.entity.Price;
import com.epam.jwd.subscription.entity.Status;
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
class StatusServiceTest {

    private MethodStatusDao dao;
    private Status status;
    private Long id;
    private List<Status> statusList;

    @BeforeAll
    void beforeAll(){
        dao = mock(MethodStatusDao.class);
        status = new Status(1L, "Active");
        id = 1L;
        statusList = new ArrayList<>();
        statusList.add(status);
    }

    @Test
    void findAll() {
        List<Status> expectedResult = statusList;
        when(dao.read()).thenReturn(expectedResult);
        List<Status> actualResult = dao.read();
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void findById() {
        Optional<Status> expectedResult = Optional.of(status);
        when(dao.read(id)).thenReturn(expectedResult);
        Optional<Status> actualResult = dao.read(id);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void create() {
        Status expectedResult = status;
        when(dao.create(status)).thenReturn(expectedResult);
        Status actualResult = dao.create(status);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void delete() {
        when(dao.delete(id)).thenReturn(true);
        boolean actualResult = dao.delete(id);
        assertTrue(actualResult);
    }
}