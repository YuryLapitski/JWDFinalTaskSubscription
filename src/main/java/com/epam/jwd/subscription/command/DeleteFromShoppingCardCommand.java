package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class DeleteFromShoppingCardCommand implements Command {

    private static final String SHOPPING_CARD_PAGE = "page.shopping_card";

    private static final String INDEX_ATTRIBUTE_NAME = "index";
    private static final String TOTAL_PRICE_ATTRIBUTE_NAME = "totalPrice";
    private static final String SUBSCRSHOWS_SESSION_ATTRIBUTE_NAME = "subscrshows";
    private static final String SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME = "subscriptions";

    private static DeleteFromShoppingCardCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private DeleteFromShoppingCardCommand(RequestFactory requestFactory,
                                          PropertyContext propertyContext) {
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static DeleteFromShoppingCardCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new DeleteFromShoppingCardCommand(
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
    @SuppressWarnings({"unchecked"})
    public CommandResponse execute(CommandRequest request) {
        if (request.retrieveFromSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME).isPresent()
                && request.retrieveFromSession(SUBSCRSHOWS_SESSION_ATTRIBUTE_NAME).isPresent()) {
            final List<SubscrShow> subscrShows =
                    (ArrayList<SubscrShow>) request.retrieveFromSession(SUBSCRSHOWS_SESSION_ATTRIBUTE_NAME).get();
            final List<Subscription> subscriptions =
                    (ArrayList<Subscription>) request.retrieveFromSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME).get();
            request.removeFromSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME);
            request.removeFromSession(SUBSCRSHOWS_SESSION_ATTRIBUTE_NAME);
            int i = Integer.parseInt(request.getParameter(INDEX_ATTRIBUTE_NAME));
            subscriptions.remove(i);
            subscrShows.remove(i);
            double sum = 0.0;
            for (SubscrShow subscr : subscrShows) {
                sum += subscr.getPrice().doubleValue();
            }
            request.addAttributeToJsp(TOTAL_PRICE_ATTRIBUTE_NAME, sum);
            request.addToSession(SUBSCRSHOWS_SESSION_ATTRIBUTE_NAME, subscrShows);
            request.addToSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME, subscriptions);
            return requestFactory.createForwardResponse(propertyContext.get(SHOPPING_CARD_PAGE));
        } else {
            return null;
        }
    }
}
