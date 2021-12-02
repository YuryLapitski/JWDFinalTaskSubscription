package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.Subscription;
import com.epam.jwd.subscription.service.ServiceFactory;
import com.epam.jwd.subscription.service.SubscriptionService;

import java.util.concurrent.locks.ReentrantLock;

public class AddToShoppingCardCommand implements Command {

    private static final String SUBSCRIBE_PAGE = "page.subscribe";
    private static final String INDEX_PAGE = "page.index";
    private static final String ERROR_LOGIN_NOT_EXIST_ATTRIBUTE = "errorLoginNotExistMessage";
    private static final String ERROR_LOGIN_NOT_EXIST_MESSAGE = "Please log in";
    private static final String ACCOUNT_SESSION_ATTRIBUTE_NAME = "account";
    private static final String SUBSCRIPTION_SESSION_ATTRIBUTE_NAME = "subscription";

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
                    instance = new AddToShoppingCardCommand(ServiceFactory.instance().subscriptionService(),
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
        if (!request.sessionExists() || !request.retrieveFromSession(ACCOUNT_SESSION_ATTRIBUTE_NAME).isPresent()) {
            request.addAttributeToJsp(ERROR_LOGIN_NOT_EXIST_ATTRIBUTE, ERROR_LOGIN_NOT_EXIST_MESSAGE);
            return requestFactory.createForwardResponse(propertyContext.get(SUBSCRIBE_PAGE));
        }
        final Long userId = Long.parseLong(request.getParameter("userId"));
        final Long addressId = Long.parseLong(request.getParameter("addressId"));
        final Long editionId = Long.parseLong(request.getParameter("editionId"));
        final Long termId = Long.parseLong(request.getParameter("termId"));
        final Long priceId = Long.parseLong(request.getParameter("priceId"));
        final Long statusId = Long.parseLong(request.getParameter("statusId"));
        Subscription newSubscription = new Subscription(userId, addressId, editionId, termId, priceId, statusId);
        subscriptionService.create(newSubscription);
        request.addToSession(SUBSCRIPTION_SESSION_ATTRIBUTE_NAME, newSubscription);
        return requestFactory.createRedirectResponse(propertyContext.get(INDEX_PAGE));
    }
}
