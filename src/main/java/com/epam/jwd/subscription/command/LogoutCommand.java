package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.SubscrShow;
import com.epam.jwd.subscription.entity.Subscription;
import com.epam.jwd.subscription.service.ServiceFactory;
import com.epam.jwd.subscription.service.SimpleSubscriptionService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class LogoutCommand implements Command {
    private static final String ACCOUNT_SESSION_ATTRIBUTE_NAME = "account";
    private static final String INDEX_PAGE = "page.index";
    private static final String SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME = "subscriptions";

    private final SimpleSubscriptionService subscriptionService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private static LogoutCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private LogoutCommand(SimpleSubscriptionService subscriptionService,
                          RequestFactory requestFactory, PropertyContext propertyContext) {
        this.subscriptionService = subscriptionService;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static LogoutCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new LogoutCommand(ServiceFactory.instance().subscriptionService(),
                            RequestFactory.getInstance(), PropertyContext.getInstance());
                }
            } finally {
                LOCK.unlock();
            }
        }
        return instance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        if (noLoggedInUserPresent(request)) {
            //todo: error - no user found cannot logout
            return null;
        }
        if (request.retrieveFromSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME).isPresent()) {
            final List<Subscription> subscriptions =
                    (ArrayList<Subscription>) request.retrieveFromSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME).get();
            for (Subscription subscription : subscriptions) {
                List<Subscription> allSubscriptions = subscriptionService.findIdByAll(subscription.getUserId(),
                        subscription.getAddressId(), subscription.getEditionId(), subscription.getTermId(),
                        subscription.getPriceId(), subscription.getStatusId());
                for (Subscription newSubscription : allSubscriptions) {
                    subscriptionService.delete(newSubscription.getId());
                }
            }
        }
        request.clearSession();
        return requestFactory.createRedirectResponse(propertyContext.get(INDEX_PAGE));
    }

    private boolean noLoggedInUserPresent(CommandRequest request) {
        return !request.sessionExists() || (
                request.sessionExists()
                        && !request.retrieveFromSession(ACCOUNT_SESSION_ATTRIBUTE_NAME)
                        .isPresent()
        );
    }
}
