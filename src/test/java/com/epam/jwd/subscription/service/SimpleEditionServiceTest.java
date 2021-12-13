package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.MethodEditionDao;
import com.epam.jwd.subscription.entity.Category;
import com.epam.jwd.subscription.entity.Edition;
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
class SimpleEditionServiceTest {

    private MethodEditionDao dao;
    private Edition edition;
    private Long id;
    private String edName;
    private List<Edition> editionList;

    @BeforeAll
    void beforeAll(){
        dao = mock(MethodEditionDao.class);
        edition = new Edition(1L, "The Times", Category.of("Book"), BigDecimal.valueOf(30.00),
                BigDecimal.valueOf(50.00), BigDecimal.valueOf(80.00));
        id = 1L;
        edName = "The Times";
        editionList = new ArrayList<>();
        editionList.add(edition);
    }

    @Test
    void findAll() {
        List<Edition> expectedResult = editionList;
        when(dao.read()).thenReturn(expectedResult);
        List<Edition> actualResult = dao.read();
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void findById() {
        Optional<Edition> expectedResult = Optional.of(edition);
        when(dao.read(id)).thenReturn(expectedResult);
        Optional<Edition> actualResult = dao.read(id);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void create() {
        Edition expectedResult = edition;
        when(dao.create(edition)).thenReturn(expectedResult);
        Edition actualResult = dao.create(edition);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void delete() {
        when(dao.delete(id)).thenReturn(true);
        boolean actualResult = dao.delete(id);
        assertTrue(actualResult);
    }

    @Test
    void findByName() {
        Optional<Edition> expectedResult = Optional.of(edition);
        when(dao.findByName(edName)).thenReturn(expectedResult);
        Optional<Edition> actualResult = dao.findByName(edName);
        assertEquals(actualResult, expectedResult);
    }
}