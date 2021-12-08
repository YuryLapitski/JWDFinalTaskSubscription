package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.*;
import com.epam.jwd.subscription.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class AddToShoppingCardCommand implements Command {

    private static final String INDEX_PAGE = "page.index";
    private static final String ERROR_LOGIN_NOT_EXIST_ATTRIBUTE = "errorLoginNotExistMessage";
    private static final String ERROR_LOGIN_NOT_EXIST_MESSAGE = "Please log in";
    private static final String ACCOUNT_SESSION_ATTRIBUTE_NAME = "account";
    private static final String SUBSCRSHOWS_SESSION_ATTRIBUTE_NAME = "subscrshows";
    private static final String SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME = "subscriptions";

    private static AddToShoppingCardCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final SubscriptionService subscriptionService;
    private final SimpleUserService userService;
    private final AddressService addressService;
    private final EditionService editionService;
    private final PriceService priceService;
    private final TermService termService;
    private final StatusService statusService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private AddToShoppingCardCommand(SubscriptionService subscriptionService,
                                     SimpleUserService userService,
                                     AddressService addressService,
                                     EditionService editionService,
                                     PriceService priceService,
                                     TermService termService,
                                     StatusService statusService,
                                     RequestFactory requestFactory,
                                     PropertyContext propertyContext) {
        this.subscriptionService = subscriptionService;
        this.userService = userService;
        this.addressService = addressService;
        this.editionService = editionService;
        this.priceService = priceService;
        this.termService = termService;
        this.statusService = statusService;
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
                            ServiceFactory.instance().userService(),
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
    @SuppressWarnings("unchecked")
    public CommandResponse execute(CommandRequest request) {
        if (request.retrieveFromSession(SUBSCRSHOWS_SESSION_ATTRIBUTE_NAME).isPresent()
                && request.retrieveFromSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME).isPresent()) {
            final List<SubscrShow> subscrShows =
                    (ArrayList<SubscrShow>) request.retrieveFromSession(SUBSCRSHOWS_SESSION_ATTRIBUTE_NAME).get();
            final List<Subscription> subscriptions =
                    (ArrayList<Subscription>) request.retrieveFromSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME).get();
            return getCommandResponse(request, subscrShows, subscriptions);
        } else {
            final List<SubscrShow> subscrShows = new ArrayList<>();
            final List<Subscription> subscriptions = new ArrayList<>();
            return getCommandResponse(request, subscrShows, subscriptions);
        }
    }

    private CommandResponse getCommandResponse(CommandRequest request, List<SubscrShow> subscrShows,
                                               List<Subscription> subscriptions) {
        Subscription subscription = createSubscription(request);
        subscriptions.add(subscription);
        SubscrShow subscrShow = createSubscrShow(subscription);
        subscrShows.add(subscrShow);
        request.addToSession(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME, subscriptions);
        request.addToSession(SUBSCRSHOWS_SESSION_ATTRIBUTE_NAME, subscrShows);
        return requestFactory.createRedirectResponse(propertyContext.get(INDEX_PAGE));
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
        Subscription subscription = new Subscription(userId, addressId, editionId, termId, priceId, statusId);
        subscriptionService.create(subscription);
        return subscription;
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
