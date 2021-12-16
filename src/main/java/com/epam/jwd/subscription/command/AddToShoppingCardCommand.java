package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.*;
import com.epam.jwd.subscription.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class AddToShoppingCardCommand implements Command {

    private static final String INDEX_PAGE = "page.index";
    private static final String ERROR_LOGIN_NOT_EXIST_ATTRIBUTE = "errorLoginNotExistMessage";
    private static final String ERROR_LOGIN_NOT_EXIST_MESSAGE = "Please log in";
    private static final String ACCOUNT_SESSION_ATTRIBUTE_NAME = "account";
    private static final String SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME = "subscriptions";

    private static AddToShoppingCardCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final SubscriptionService subscriptionService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private AddToShoppingCardCommand(SubscriptionService subscriptionService,
                                     RequestFactory requestFactory,
                                     PropertyContext propertyContext) {
        this.subscriptionService = subscriptionService;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static AddToShoppingCardCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new AddToShoppingCardCommand(
                            ServiceFactory.instance().subscriptionService(),
                            RequestFactory.getInstance(), 
                            PropertyContext.getInstance());
                }
            } finally {
                LOCK.unlock();
            }
        }
        return instance;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CommandResponse execute(CommandRequest request) {
        if (request.retrieveFromSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME).isPresent()) {
            final List<Subscription> subscriptions =
                    (ArrayList<Subscription>) request.retrieveFromSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME).get();
            return getCommandResponse(request, subscriptions);
        } else {
            final List<Subscription> subscriptions = new ArrayList<>();
            return getCommandResponse(request, subscriptions);
        }
    }

    private CommandResponse getCommandResponse(CommandRequest request,
                                               List<Subscription> subscriptions) {
        Subscription sub = createSubscription(request);
        final List<Subscription> subList = subscriptionService.findIdByAll(sub.getUserId(), sub.getAddressId(),
                sub.getEditionId(), sub.getTermId(), sub.getPriceId(), sub.getStatusId());
        for (Subscription subscription : subList) {
            subscriptionService.delete(subscription.getId());
        }
        subscriptions.add(sub);
        request.addToSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME, subscriptions);
        return requestFactory.createRedirectResponse(propertyContext.get(INDEX_PAGE));
    }

    private Subscription createSubscription(CommandRequest request) {
        if (!request.sessionExists() || !request.retrieveFromSession(ACCOUNT_SESSION_ATTRIBUTE_NAME).isPresent()) {
            request.addAttributeToJsp(ERROR_LOGIN_NOT_EXIST_ATTRIBUTE, ERROR_LOGIN_NOT_EXIST_MESSAGE);
        }
        final Long userId = Long.parseLong(request.getParameter("userId"));
        final Long addressId = Long.parseLong(request.getParameter("addressId"));
        final Long editionId = Long.parseLong(request.getParameter("editionId"));
        final Long termId = Long.parseLong(request.getParameter("termId"));
        final Long priceId = Long.parseLong(request.getParameter("priceId"));
        final Long statusId = Long.parseLong(request.getParameter("statusId"));
        return new Subscription(userId, addressId, editionId, termId, priceId, statusId);
    }
}
