package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.MethodUserDao;
import com.epam.jwd.subscription.entity.User;
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
class SimpleUserServiceTest {

    private MethodUserDao dao;
    private User user;
    private Long id;
    private Long accId;
    List<User> userList;

    @BeforeAll
    void beforeAll(){
        dao = mock(MethodUserDao.class);
        user = new User(1L,"Santa", "Claus", 62, "santa-claus@yahoo.com", 1L);
        id = 1L;
        accId = 1L;
        userList = new ArrayList<>();
        userList.add(user);
    }

    @Test
    void findAll() {
        List<User> expectedResult = userList;
        when(dao.read()).thenReturn(expectedResult);
        List<User> actualResult = dao.read();
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void findById() {
        Optional<User> expectedResult = Optional.of(user);
        when(dao.read(id)).thenReturn(expectedResult);
        Optional<User> actualResult = dao.read(id);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void create() {
        User expectedResult = user;
        when(dao.create(user)).thenReturn(expectedResult);
        User actualResult = dao.create(user);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void delete() {
        when(dao.delete(id)).thenReturn(true);
        boolean actualResult = dao.delete(id);
        assertTrue(actualResult);
    }

    @Test
    void readUserByAccountId() {
        Optional<User> expectedResult = Optional.of(user);
        when(dao.readUserByAccountId(accId)).thenReturn(expectedResult);
        Optional<User> actualResult = dao.readUserByAccountId(id);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void updateByAccountId() {
        User expectedResult = user;
        when(dao.update(user)).thenReturn(expectedResult);
        User actualResult = dao.update(user);
        assertEquals(actualResult, expectedResult);
    }
}