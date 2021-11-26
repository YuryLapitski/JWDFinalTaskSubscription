package com.epam.jwd.subscription.entity;

import com.sun.org.apache.bcel.internal.classfile.Unknown;
import org.omg.CORBA.UNKNOWN;

import java.util.Arrays;
import java.util.List;

public enum Term {

    THREE (3),
    SIX (6),
    TWELVE (12);

    private static final List<Term> ALL_AVAILABLE_TERMS = Arrays.asList(values());

    private final int months;

    Term(int months) {
        this.months = months;
    }

    public int getMonths() {
        return months;
    }

    public static List<Term> valuesAsList() {
        return ALL_AVAILABLE_TERMS;
    }

    public static Term of(int months) {
        for (Term term : values()) {
            if (term.getMonths() == months) {
                return term;
            }
        }
        return null;
    }
}
