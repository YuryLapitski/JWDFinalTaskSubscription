package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.entity.Term;

public interface TermDao extends EntityDao<Term> {

    static TermDao instance() {
        return MethodTermDao.getInstance();
    }
}
