package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.*;
import com.epam.jwd.subscription.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class ShowConfirmPaymentCommand implements Command {

    private static final String CONFIRM_PAGE = "page.confirmation";
    private static final String SHOPPING_CARD_PAGE = "page.shopping_card";
    private static final String SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME = "subscriptions";
    private static final String SUBSCRSHOWS_SESSION_ATTRIBUTE_NAME = "subscrshows";
    private static final String ACCOUNT_SESSION_ATTRIBUTE_NAME = "account";
    private static final String CARD_NUMBER_REQUEST_PARAM_NAME = "card_number";
    private static final String TOTAL_PRICE_REQUEST_PARAM_NAME = "totalPrice";
    private static final String TOTAL_PRICE_ATTRIBUTE_NAME = "totalPrice";
    private static final Long ACTIVE_STATUS_ID = 2L;
    private static final String ADMIN_CARD_NUMBER = "0001000200030004";
    private static final String ERROR_CARD_ATTRIBUTE = "errorCardMessage";
    private static final String ERROR_CARD_MESSAGE = "No such card";
    private static final String ERROR_CHOOSE_ATTRIBUTE = "errorChooseMessage";
    private static final String ERROR_CHOOSE_MESSAGE = "You have not chosen anything";
    private static final String ERROR_AMOUNT_ATTRIBUTE = "errorAmountMessage";
    private static final String ERROR_AMOUNT_MESSAGE = "Not enough money";

    private static ShowConfirmPaymentCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final CardService cardService;
    private final SubscriptionService subscriptionService;
    private final ArchiveService archiveService;
    private final AddressService addressService;
    private final EditionService editionService;
    private final PriceService priceService;
    private final TermService termService;
    private final StatusService statusService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private ShowConfirmPaymentCommand(CardService cardService,
                                      SubscriptionService subscriptionService,
                                      ArchiveService archiveService,
                                      AddressService addressService,
                                      EditionService editionService,
                                      PriceService priceService,
                                      TermService termService,
                                      StatusService statusService,
                                      RequestFactory requestFactory,
                                      PropertyContext propertyContext) {
        this.cardService = cardService;
        this.subscriptionService = subscriptionService;
        this.archiveService = archiveService;
        this.addressService = addressService;
        this.editionService = editionService;
        this.priceService = priceService;
        this.termService = termService;
        this.statusService = statusService;
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
                            ServiceFactory.instance().archiveService(),
                            ServiceFactory.instance().addressService(),
                            ServiceFactory.instance().editionService(),
                            ServiceFactory.instance().priceService(),
                            ServiceFactory.instance().termService(),
                            ServiceFactory.instance().statusService(),
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
            if (cardService.transferMoney(cardFrom(request), cardTo(), totalPrice)) {
                final List<Subscription> subscriptions = subscriptions(request);
                if (subscriptions != null) {
                    for (Subscription subscription : subscriptions) {
                        final List<Subscription> subscriptionList = subscriptionService.findIdByAll(subscription.getUserId(),
                                subscription.getAddressId(), subscription.getEditionId(), subscription.getTermId(),
                                subscription.getPriceId(), subscription.getStatusId());
                        for (Subscription subscr : subscriptionList) {
                            subscriptionService.updateStatus(ACTIVE_STATUS_ID, subscr.getId());
                            if (subscriptionService.findById(subscr.getId()).isPresent()) {
                                Subscription updSub = subscriptionService.findById(subscr.getId()).get();
                                archiveService.create(addToArchive(request, updSub));
                            }
                        }
                    }
                    request.removeFromSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME);
                    request.removeFromSession(SUBSCRSHOWS_SESSION_ATTRIBUTE_NAME);
                    return requestFactory.createForwardResponse(propertyContext.get(CONFIRM_PAGE));
                } else {
                    request.addAttributeToJsp(ERROR_CHOOSE_ATTRIBUTE, ERROR_CHOOSE_MESSAGE);
                    return requestFactory.createForwardResponse(propertyContext.get(SHOPPING_CARD_PAGE));
                }
            } else {
                request.addAttributeToJsp(ERROR_AMOUNT_ATTRIBUTE, ERROR_AMOUNT_MESSAGE);
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

    @SuppressWarnings("unchecked")
    private List<Subscription> subscriptions(CommandRequest request) {
        if (request.retrieveFromSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME).isPresent()) {
            return (ArrayList<Subscription>) request.retrieveFromSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME).get();
        }
        return null;
    }

    private Archive addToArchive(CommandRequest request, Subscription subscription) {
        if (findAccount(request) != null || findEdition(subscription.getEditionId()) != null
        || findTerm(subscription.getTermId()) != null || findPrice(subscription.getPriceId()) != null
        || findAddress(subscription.getAddressId()) != null || findStatus(subscription.getStatusId()) != null) {
            return new Archive(Objects.requireNonNull(findAccount(request)).getId(),
                    Objects.requireNonNull(findEdition(subscription.getEditionId())).getName(),
                    Objects.requireNonNull(findTerm(subscription.getTermId())).getMonths(),
                    Objects.requireNonNull(findPrice(subscription.getPriceId())).getValue(),
                    Objects.requireNonNull(findAddress(subscription.getAddressId())).getCity(),
                    Objects.requireNonNull(findAddress(subscription.getAddressId())).getStreet(),
                    Objects.requireNonNull(findAddress(subscription.getAddressId())).getHouse(),
                    Objects.requireNonNull(findAddress(subscription.getAddressId())).getFlat(),
                    Objects.requireNonNull(findStatus(subscription.getStatusId())).getStatus());
        } else {
            return null;
        }
    }

    private Account findAccount(CommandRequest request) {
        if (request.retrieveFromSession(ACCOUNT_SESSION_ATTRIBUTE_NAME).isPresent()) {
            return (Account) request.retrieveFromSession(ACCOUNT_SESSION_ATTRIBUTE_NAME).get();
        } else {
            return null;
        }
    }

    private Edition findEdition(Long id) {
        if (editionService.findById(id).isPresent()) {
            return editionService.findById(id).get();
        } else {
            return null;
        }
    }

    private Address findAddress(Long id) {
        if (addressService.findById(id).isPresent()) {
            return addressService.findById(id).get();
        } else {
            return null;
        }
    }

    private Term findTerm(Long id) {
        if (termService.findById(id).isPresent()) {
            return termService.findById(id).get();
        } else {
            return null;
        }
    }

    private Price findPrice(Long id) {
        if (priceService.findById(id).isPresent()) {
            return priceService.findById(id).get();
        } else {
            return null;
        }
    }

    private Status findStatus(Long id) {
        if (statusService.findById(id).isPresent()) {
            return statusService.findById(id).get();
        } else {
            return null;
        }
    }
}
