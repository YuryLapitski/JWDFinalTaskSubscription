package com.epam.jwd.subscription.controller;

import com.epam.jwd.subscription.command.CommandRequest;
import com.epam.jwd.subscription.command.CommandResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleRequestFactory implements RequestFactory {

    private final Map<String, CommandResponse> forwardResponceCache = new ConcurrentHashMap<>();
    private final Map<String, CommandResponse> redirectResponceCache = new ConcurrentHashMap<>();

    private SimpleRequestFactory() {
    }

    private static class Holder {
        public static final SimpleRequestFactory INSTANCE = new SimpleRequestFactory();
    }

    static SimpleRequestFactory getInstance() {
        return SimpleRequestFactory.Holder.INSTANCE;
    }

    @Override
    public CommandRequest createRequest(HttpServletRequest request) {
        return new WrappingCommandRequest(request);
    }

    @Override
    public CommandResponse createForwardResponse(String path) {
        return forwardResponceCache.computeIfAbsent(path, PlainCommandResponce::new);
    }

    @Override
    public CommandResponse createRedirectResponse(String path) {
        return redirectResponceCache.computeIfAbsent(path, p -> new PlainCommandResponce(true, p));
    }
}
