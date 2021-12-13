package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.MethodArchiveDao;
import com.epam.jwd.subscription.entity.Archive;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SimpleArchiveServiceTest {

    private MethodArchiveDao dao;
    private Archive archive;
    private Long id;
    private Long accId;
    private List<Archive> archiveList;

    @BeforeAll
    void beforeAll(){
        dao = mock(MethodArchiveDao.class);
        archive = new Archive(1L,1L, "The Times", 3, BigDecimal.valueOf(30.50),
                "Minsk", "ul. Gromova", "53/1", 30, "Active",
                Timestamp.valueOf("2021-12-11 18:21:12"));
        id = 1L;
        accId = 1L;
        archiveList = new ArrayList<>();
        archiveList.add(archive);
    }

    @Test
    void findByAccId() {
        List<Archive> expectedResult = archiveList;
        when(dao.findByAccId(accId)).thenReturn(expectedResult);
        List<Archive> actualResult = dao.findByAccId(accId);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void findAll() {
        List<Archive> expectedResult = archiveList;
        when(dao.read()).thenReturn(expectedResult);
        List<Archive> actualResult = dao.read();
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void findById() {
        Optional<Archive> expectedResult = Optional.of(archive);
        when(dao.read(id)).thenReturn(expectedResult);
        Optional<Archive> actualResult = dao.read(id);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void create() {
        Archive expectedResult = archive;
        when(dao.create(archive)).thenReturn(expectedResult);
        Archive actualResult = dao.create(archive);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void delete() {
        when(dao.delete(id)).thenReturn(true);
        boolean actualResult = dao.delete(id);
        assertTrue(actualResult);
    }
}