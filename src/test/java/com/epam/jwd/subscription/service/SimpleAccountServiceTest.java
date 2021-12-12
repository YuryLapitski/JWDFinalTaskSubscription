package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.MethodAccountDao;
import com.epam.jwd.subscription.entity.Account;
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
class SimpleAccountServiceTest {

    private MethodAccountDao dao;
    private Account account;
    private Long id;
    private List<Account> accountList;

    @BeforeAll
    void beforeAll(){
        dao = mock(MethodAccountDao.class);
        account = new Account(1L,"Santa", "test", 1, Role.USER);
        id = 1L;
        accountList = new ArrayList<>();
        accountList.add(account);
    }

    @Test
    void findAll() {
        List<Account> expectedResult = accountList;
        when(dao.read()).thenReturn(expectedResult);
        List<Account> actualResult = dao.read();
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void findById() {
        Optional<Account> expectedResult = Optional.of(account);
        when(dao.read(id)).thenReturn(expectedResult);
        Optional<Account> actualResult = dao.read(id);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void create() {
        Account expectedResult = account;
        when(dao.create(account)).thenReturn(expectedResult);
        Account actualResult = dao.create(account);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void delete() {
        when(dao.delete(id)).thenReturn(true);
        boolean actualResult = dao.delete(id);
        assertTrue(actualResult);
    }
}