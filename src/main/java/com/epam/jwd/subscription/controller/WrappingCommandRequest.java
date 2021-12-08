package com.epam.jwd.subscription.controller;

import com.epam.jwd.subscription.command.CommandRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class WrappingCommandRequest implements CommandRequest {

    private final HttpServletRequest request;

    public WrappingCommandRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void addAttributeToJsp(String name, Object attribute) {
        request.setAttribute(name, attribute);

    }

    @Override
    public String getParameter(String name) {
        return request.getParameter(name);
    }

    @Override
    public boolean sessionExists() {
        return request.getSession(false) != null;
    }

    @Override
    public boolean addToSession(String name, Object value) {
        final HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute(name, value);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Object> retrieveFromSession(String name) {
        return Optional.ofNullable(request.getSession(false))
                .map(session -> session.getAttribute(name));
    }

    @Override
    public void clearSession() {
        final HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    @Override
    public void createSession() {
        request.getSession(true);
    }

    @Override
    public void removeFromSession(String name) {
        HttpSession session = request.getSession();
        session.removeAttribute(name);
    }

    @Override
    public String[] getParameters (String name) {
        return request.getParameterValues(name);
    }

    @Override
    public Cookie[] findCookies() {
        return request.getCookies();
    }

}
