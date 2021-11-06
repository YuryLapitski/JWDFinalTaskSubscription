package com.epam.jwd.subscription.controller;

import com.epam.jwd.subscription.command.CommandResponse;

import java.util.Objects;

public class PlainCommandResponce implements CommandResponse {

    private final boolean redirect;
    private final String path;

    public PlainCommandResponce(String path) {
        this(false, path);
    }

    public PlainCommandResponce(boolean redirect, String path) {
        this.redirect = redirect;
        this.path = path;
    }

    @Override
    public boolean isRedirect() {
        return redirect;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlainCommandResponce that = (PlainCommandResponce) o;
        return redirect == that.redirect &&
                Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(redirect, path);
    }

    @Override
    public String toString() {
        return "PlainCommandResponce{" +
                "redirect=" + redirect +
                ", path='" + path + '\'' +
                '}';
    }
}
