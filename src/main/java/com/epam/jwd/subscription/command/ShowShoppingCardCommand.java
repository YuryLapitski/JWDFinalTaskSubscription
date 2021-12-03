package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.*;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class ShowShoppingCardCommand implements Command {

    private static final String SHOPPING_CARD_PAGE = "page.shopping_card";
    private static final String SUBSCRIPTION_SESSION_ATTRIBUTE_NAME = "subscription";
    private static final String SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME = "subscriptions";
    private static final String TOTAL_PRICE_ATTRIBUTE_NAME = "totalPrice";
    private static final String ERROR_SUBSCRIPTION_ATTRIBUTE = "errorLoginPassMessage";
    private static final String ERROR_SUBSCRIPTION_MESSAGE = "No subscriptions found";


    private static ShowShoppingCardCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private ShowShoppingCardCommand(RequestFactory requestFactory, PropertyContext propertyContext) {
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static ShowShoppingCardCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowShoppingCardCommand(RequestFactory.getInstance(),
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
        if (request.retrieveFromSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME).isPresent()) {
            ArrayList<SubscrShow> subscriptions =
                    (ArrayList<SubscrShow>) request.retrieveFromSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME).get();
            double sum = 0.0;
            for (SubscrShow subscr : subscriptions) {
                sum += subscr.getPrice().doubleValue();
            }
            request.addAttributeToJsp(TOTAL_PRICE_ATTRIBUTE_NAME, sum);
            request.addAttributeToJsp(SUBSCRIPTION_SESSION_ATTRIBUTE_NAME, subscriptions);
        } else {
            request.addAttributeToJsp(ERROR_SUBSCRIPTION_ATTRIBUTE, ERROR_SUBSCRIPTION_MESSAGE);
        }
        return requestFactory.createForwardResponse(propertyContext.get(SHOPPING_CARD_PAGE));
    }
}
