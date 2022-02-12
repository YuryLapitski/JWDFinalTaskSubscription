package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.*;
import com.epam.jwd.subscription.service.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class ShowShoppingCardCommand implements Command {

    private static final String SHOPPING_CARD_PAGE = "page.shopping_card";
    private static final String SUBSCRSHOWS_SESSION_ATTRIBUTE_NAME = "subscrshows";
    private static final String SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME = "subscriptions";
    private static final String TOTAL_PRICE_ATTRIBUTE_NAME = "totalPrice";
    private static final String ERROR_SUBSCRIPTION_ATTRIBUTE = "errorLoginPassMessage";
    private static final String ERROR_SUBSCRIPTION_MESSAGE = "No subscriptions found";


    private static ShowShoppingCardCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final UserService userService;
    private final AddressService addressService;
    private final EditionService editionService;
    private final PriceService priceService;
    private final TermService termService;
    private final StatusService statusService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private ShowShoppingCardCommand(UserService userService,
                                    AddressService addressService,
                                    EditionService editionService,
                                    PriceService priceService,
                                    TermService termService,
                                    StatusService statusService,
                                    RequestFactory requestFactory,
                                    PropertyContext propertyContext) {
        this.userService = userService;
        this.addressService = addressService;
        this.editionService = editionService;
        this.priceService = priceService;
        this.termService = termService;
        this.statusService = statusService;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static ShowShoppingCardCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowShoppingCardCommand(
                            ServiceFactory.getInstance().userService(),
                            ServiceFactory.getInstance().addressService(),
                            ServiceFactory.getInstance().editionService(),
                            ServiceFactory.getInstance().priceService(),
                            ServiceFactory.getInstance().termService(),
                            ServiceFactory.getInstance().statusService(),
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
            ArrayList<Subscription> subList =
                    (ArrayList<Subscription>) request.retrieveFromSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME).get();
            request.removeFromSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME);
            ArrayList<SubscrShow> subscrShows = new ArrayList<>();
            for (Subscription subscription : subList) {
                subscrShows.add(createSubscrShow(subscription));
            }
            double sum = 0.0;
            for (SubscrShow subscr : subscrShows) {
                sum += subscr.getPrice().doubleValue();
            }
            request.addAttributeToJsp(TOTAL_PRICE_ATTRIBUTE_NAME, sum);
            request.addToSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME, subList);
            request.addToSession(SUBSCRSHOWS_SESSION_ATTRIBUTE_NAME, subscrShows);
        } else {
            request.addAttributeToJsp(ERROR_SUBSCRIPTION_ATTRIBUTE, ERROR_SUBSCRIPTION_MESSAGE);
        }
        return requestFactory.createForwardResponse(propertyContext.get(SHOPPING_CARD_PAGE));
    }

    private SubscrShow createSubscrShow(Subscription subscription) {
        return new SubscrShow(subscription.getId(),
                findUser(subscription.getUserId()).getFirstName(),
                findUser(subscription.getUserId()).getLastName(),
                findAddress(subscription.getAddressId()).getCity(),
                findAddress(subscription.getAddressId()).getStreet(),
                findAddress(subscription.getAddressId()).getHouse(),
                findAddress(subscription.getAddressId()).getFlat(),
                findEdition(subscription.getEditionId()).getName(),
                findTerm(subscription.getTermId()).getMonths(),
                findPrice(subscription.getPriceId()).getValue(),
                findStatus(subscription.getStatusId()).getStatus());
    }

    private User findUser(Long userId) {
        Optional<User> optionalUser = userService.findById(userId);
        return optionalUser.orElse(null);
    }

    private Address findAddress(Long addressId) {
        Optional<Address> optionalAddress = addressService.findById(addressId);
        return optionalAddress.orElse(null);
    }

    private Edition findEdition(Long editionId) {
        Optional<Edition> optionalEdition = editionService.findById(editionId);
        return optionalEdition.orElse(null);
    }

    private Term findTerm(Long termId) {
        Optional<Term> optionalTerm = termService.findById(termId);
        return optionalTerm.orElse(null);
    }

    private Price findPrice(Long priceId) {
        Optional<Price> optionalPrice = priceService.findById(priceId);
        return optionalPrice.orElse(null);
    }

    private Status findStatus(Long statusId) {
        Optional<Status> optionalStatus = statusService.findById(statusId);
        return optionalStatus.orElse(null);
    }
}
