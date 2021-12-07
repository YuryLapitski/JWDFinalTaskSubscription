package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.TermDao;
import com.epam.jwd.subscription.entity.Term;

import java.util.List;
import java.util.Optional;

public class TermService implements EntityService<Term> {

    private final TermDao termDao;

    public TermService(TermDao termDao) {
        this.termDao = termDao;
    }

    @Override
    public List<Term> findAll() {
        return null;
    }

    @Override
    public Optional<Term> findById(Long id) {
        return termDao.read(id);
    }

    @Override
    public Optional<Term> create(Term entity) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return termDao.delete(id);
    }
}
