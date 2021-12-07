package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.Card;
import com.epam.jwd.subscription.entity.Subscription;
import com.epam.jwd.subscription.service.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class ShowConfirmPaymentCommand implements Command {

    private static final String CONFIRM_PAGE = "page.confirmation";
    private static final String SHOPPING_CARD_PAGE = "page.shopping_card";
    private static final String SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME = "subscriptions";
    private static final String SUBSCRSHOWS_SESSION_ATTRIBUTE_NAME = "subscrshows";
    private static final String CARD_NUMBER_REQUEST_PARAM_NAME = "card_number";
    private static final String TOTAL_PRICE_REQUEST_PARAM_NAME = "totalPrice";
    private static final String TOTAL_PRICE_ATTRIBUTE_NAME = "totalPrice";
    private static final Long ACTIVE_STATUS_ID = 2L;
    private static final String ADMIN_CARD_NUMBER = "0001000200030004";
    private static final String ERROR_CARD_ATTRIBUTE = "errorCardMessage";
    private static final String ERROR_CARD_MESSAGE = "No such card";
    private static final String ERROR_CHOOSE_ATTRIBUTE = "errorChooseMessage";
    private static final String ERROR_CHOOSE_MESSAGE = "You have not chosen anything";

    private static ShowConfirmPaymentCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final CardService cardService;
    private final SimpleSubscriptionService subscriptionService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private ShowConfirmPaymentCommand(CardService cardService,
                                      SimpleSubscriptionService subscriptionService,
                                     RequestFactory requestFactory,
                                     PropertyContext propertyContext) {
        this.cardService = cardService;
        this.subscriptionService = subscriptionService;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static ShowConfirmPaymentCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowConfirmPaymentCommand(
                            ServiceFactory.instance().cardService(),
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
    public CommandResponse execute(CommandRequest request) {
        if (!request.retrieveFromSession(SUBSCRSHOWS_SESSION_ATTRIBUTE_NAME).isPresent()
                && !request.retrieveFromSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME).isPresent()) {
            request.addAttributeToJsp(ERROR_CHOOSE_ATTRIBUTE, ERROR_CHOOSE_MESSAGE);
            return requestFactory.createForwardResponse(propertyContext.get(SHOPPING_CARD_PAGE));
        }
        if (cardFrom(request) == null) {
            request.addAttributeToJsp(ERROR_CARD_ATTRIBUTE, ERROR_CARD_MESSAGE);
            final double totalPrice = Double.parseDouble(request.getParameter(TOTAL_PRICE_REQUEST_PARAM_NAME));
            request.addAttributeToJsp(TOTAL_PRICE_ATTRIBUTE_NAME, totalPrice);
            return requestFactory.createForwardResponse(propertyContext.get(SHOPPING_CARD_PAGE));
        } else {
            final double totalPrice = Double.parseDouble(request.getParameter(TOTAL_PRICE_REQUEST_PARAM_NAME));
            cardService.transferMoney(cardFrom(request), cardTo(), BigDecimal.valueOf(totalPrice));
            final List<Subscription> subscriptions = subscriptions(request);
            if (subscriptions != null) {
                for (Subscription subscription : subscriptions) {
                    final List<Subscription> subscriptionList = subscriptionService.findIdByAll(subscription.getUserId(),
                            subscription.getAddressId(), subscription.getEditionId(), subscription.getTermId(),
                            subscription.getPriceId(), subscription.getStatusId());
                    for (Subscription subscr : subscriptionList) {
                        subscriptionService.updateStatus(ACTIVE_STATUS_ID, subscr.getId());
                    }
                }
                request.removeFromSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME);
                request.removeFromSession(SUBSCRSHOWS_SESSION_ATTRIBUTE_NAME);
                return requestFactory.createForwardResponse(propertyContext.get(CONFIRM_PAGE));
            } else {
                request.addAttributeToJsp(ERROR_CHOOSE_ATTRIBUTE, ERROR_CHOOSE_MESSAGE);
                return requestFactory.createForwardResponse(propertyContext.get(SHOPPING_CARD_PAGE));
            }
        }
    }

    private Card cardFrom(CommandRequest request) {
        final String cardNumber = request.getParameter(CARD_NUMBER_REQUEST_PARAM_NAME);
        Optional<Card> optionalCard = cardService.readCardByNumber(cardNumber);
        return optionalCard.orElse(null);
    }

    private Card cardTo() {
        Optional<Card> optionalCard = cardService.readCardByNumber(ADMIN_CARD_NUMBER);
        return optionalCard.orElse(null);
    }

    private List<Subscription> subscriptions(CommandRequest request) {
        if (request.retrieveFromSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME).isPresent()) {
            return (ArrayList<Subscription>) request.retrieveFromSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME).get();
        }
        return null;
    }
}
