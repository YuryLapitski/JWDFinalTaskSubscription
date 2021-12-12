package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.MethodCardDao;
import com.epam.jwd.subscription.entity.Card;
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
class SimpleCardServiceTest {

    private MethodCardDao dao;
    private Card card;
    private Long id;
    private String cardNumber;
    private BigDecimal amount;
    private List<Card> cardList;

    @BeforeAll
    void beforeAll(){
        dao = mock(MethodCardDao.class);
        card = new Card(1L,"Visa", "1234567812345678", BigDecimal.valueOf(1000.00));
        id = 1L;
        cardNumber = "1234567812345678";
        amount = BigDecimal.valueOf(1000.00);
        cardList = new ArrayList<>();
        cardList.add(card);
    }

    @Test
    void findAll() {
        List<Card> expectedResult = cardList;
        when(dao.read()).thenReturn(expectedResult);
        List<Card> actualResult = dao.read();
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void findById() {
        Optional<Card> expectedResult = Optional.of(card);
        when(dao.read(id)).thenReturn(expectedResult);
        Optional<Card> actualResult = dao.read(id);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void create() {
        Card expectedResult = card;
        when(dao.create(card)).thenReturn(expectedResult);
        Card actualResult = dao.create(card);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void delete() {
        when(dao.delete(id)).thenReturn(true);
        boolean actualResult = dao.delete(id);
        assertTrue(actualResult);
    }

    @Test
    void readCardByNumber() {
        Optional<Card> expectedResult = Optional.of(card);
        when(dao.readCardByNumber(cardNumber)).thenReturn(expectedResult);
        Optional<Card> actualResult = dao.readCardByNumber(cardNumber);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    void transferMoney() {
        Card to = new Card(1L,"Visa", "0001000200030004", BigDecimal.valueOf(1000.00));
        when(dao.updateAmount(amount, cardNumber)).thenReturn(true);
        when(dao.updateAmount(to.getAmount(), to.getCardNumber())).thenReturn(true);
        SimpleCardService cardService = new SimpleCardService(dao);
        final double transAmount = 100.00;
        boolean actualResult = cardService.transferMoney(card, to, transAmount);
        assertTrue(actualResult);
    }

    @Test
    void transferMoneyNegative() {
        Card to = new Card(1L,"Visa", "0001000200030004", BigDecimal.valueOf(1000.00));
        when(dao.updateAmount(amount, cardNumber)).thenReturn(true);
        when(dao.updateAmount(to.getAmount(), to.getCardNumber())).thenReturn(true);
        SimpleCardService cardService = new SimpleCardService(dao);
        final double transAmount = 10000.00;
        boolean actualResult = cardService.transferMoney(card, to, transAmount);
        assertFalse(actualResult);
    }
}