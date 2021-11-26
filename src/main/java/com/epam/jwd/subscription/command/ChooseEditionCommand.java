package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.Account;
import com.epam.jwd.subscription.entity.Category;
import com.epam.jwd.subscription.entity.Edition;
import com.epam.jwd.subscription.service.AccountService;
import com.epam.jwd.subscription.service.EditionService;
import com.epam.jwd.subscription.service.EntityService;
import com.epam.jwd.subscription.service.ServiceFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ChooseEditionCommand implements Command {

    private static final String SUBSCRIBE_PAGE = "page.subscribe";
    private static final String EDITIONS_ATTRIBUTE_NAME = "editions";
    private static final String JSP_EDITION_ID_ATTRIBUTE_NAME = "editionId";
    private static final String JSP_EDITION_NAME_ATTRIBUTE_NAME = "editionName";
    private static final String NAME_REQUEST_PARAM_NAME = "name";

    private static ChooseEditionCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private ChooseEditionCommand(RequestFactory requestFactory, PropertyContext propertyContext) {
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static ChooseEditionCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ChooseEditionCommand(RequestFactory.getInstance(),
                            PropertyContext.getInstance());
                }
            } finally {
                LOCK.unlock();
            }
        }
        return instance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final String name = request.getParameter(NAME_REQUEST_PARAM_NAME);
        request.addAttributeToJsp(JSP_EDITION_NAME_ATTRIBUTE_NAME, name);
        return requestFactory.createForwardResponse(propertyContext.get(SUBSCRIBE_PAGE));
    }
}
