package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.*;
import com.epam.jwd.subscription.service.*;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class ShowSubscriptionDataCommand implements Command {

    private static final String SUBSCRIBE_PAGE = "page.subscribe";
    private static final String TERM_PAGE = "page.term";
    private static final String EDITION_ATTRIBUTE_NAME = "edition";
    private static final String PRICE_ATTRIBUTE_NAME = "price";
    private static final String TERM_ATTRIBUTE_NAME = "term";
    private static final String JSP_USER_ATTRIBUTE_NAME = "user";
    private static final String EDITION_ID_REQUEST_PARAM_NAME = "editionId";
    private static final String ERROR_USER_DOES_NOT_EXIST_MESSAGE = "At first fill in user data";
    private static final String ERROR_FIND_USER_ATTRIBUTE = "errorFindUserMessage";
    private static final String ACCOUNT_SESSION_ATTRIBUTE_NAME = "account";
    private static final String CITY_REQUEST_PARAM_NAME = "city";
    private static final String STREET_REQUEST_PARAM_NAME = "street";
    private static final String HOUSE_REQUEST_PARAM_NAME = "house";
    private static final String FLAT_REQUEST_PARAM_NAME = "flat";
    private static final String TERM_REQUEST_PARAM_NAME = "monthsTerm";
    private static final String ADDRESS_ATTRIBUTE_NAME = "address";
    private static final Long THREE_MONTHS_TERM_ID = 1L;
    private static final Long SIX_MONTHS_TERM_ID = 2L;
    private static final Long TWELVE_MONTHS_TERM_ID = 3L;
    private static final String ERROR_ADDRESS_ATTRIBUTE = "errorAddressMessage";
    private static final String ERROR_ADDRESS_NOT_FOUND_MESSAGE = "Enter address";
    private static final String ERROR_EDITION_ATTRIBUTE = "errorEditionMessage";
    private static final String ERROR_EDITION_NOT_FOUND_MESSAGE = "Edition not found";
    private static final String ERROR_NOT_ENOUGH_DATA_ATTRIBUTE = "errorNotEnoughDataMessage";
    private static final String ERROR_NOT_ENOUGH_DATA_MESSAGE = "Not Enough Data";

    private static ShowSubscriptionDataCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final UserService userService;
    private final AddressService addressService;
    private final EditionService editionService;
    private final PriceService priceService;
    private final TermService termService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;
    private ShowSubscriptionDataCommand(UserService userService,
                                        AddressService addressService,
                                        EditionService editionService,
                                        PriceService priceService,
                                        TermService termService,
                                        RequestFactory requestFactory,
                                        PropertyContext propertyContext) {
        this.userService = userService;
        this.addressService = addressService;
        this.editionService = editionService;
        this.priceService = priceService;
        this.termService = termService;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static ShowSubscriptionDataCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowSubscriptionDataCommand(ServiceFactory.instance().userService(),
                            ServiceFactory.instance().addressService(),
                            ServiceFactory.instance().editionService(),
                            ServiceFactory.instance().priceService(),
                            ServiceFactory.instance().termService(),
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
        if (addUserToJSP(request) && addAddressToJSP(request)
                && addEditionToJSP(request) && addPriceToJSP(request)) {
            return requestFactory.createForwardResponse(propertyContext.get(SUBSCRIBE_PAGE));
        } else {
            request.addAttributeToJsp(ERROR_NOT_ENOUGH_DATA_ATTRIBUTE, ERROR_NOT_ENOUGH_DATA_MESSAGE);
            return requestFactory.createForwardResponse(propertyContext.get(TERM_PAGE));
        }
    }

    private boolean addUserToJSP (CommandRequest request) {
        Optional<User> optionalUser = userService.findById(getAccountId(request));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            request.addAttributeToJsp(JSP_USER_ATTRIBUTE_NAME, user);
            return true;
        } else {
            request.addAttributeToJsp(ERROR_FIND_USER_ATTRIBUTE, ERROR_USER_DOES_NOT_EXIST_MESSAGE);
            return false;
        }
    }

    private boolean addAddressToJSP (CommandRequest request) {
        final String city = request.getParameter(CITY_REQUEST_PARAM_NAME);
        final String street = request.getParameter(STREET_REQUEST_PARAM_NAME);
        final String house = request.getParameter(HOUSE_REQUEST_PARAM_NAME);
        final Integer flat = Integer.parseInt(request.getParameter(FLAT_REQUEST_PARAM_NAME));
        if (addressService.findByCSHF(city, street, house, flat).isPresent()) {
            Address address = addressService.findByCSHF(city, street, house, flat).get();
            request.addAttributeToJsp(ADDRESS_ATTRIBUTE_NAME, address);
            return true;
        } else {
            request.addAttributeToJsp(ERROR_ADDRESS_ATTRIBUTE, ERROR_ADDRESS_NOT_FOUND_MESSAGE);
            return false;
        }
    }

    private boolean addEditionToJSP (CommandRequest request) {
        final String editionId = request.getParameter(EDITION_ID_REQUEST_PARAM_NAME);
        final Optional<Edition> optionalEdition = editionService.findById(Long.parseLong(editionId));
        if (optionalEdition.isPresent()) {
            Edition edition = optionalEdition.get();
            request.addAttributeToJsp(EDITION_ATTRIBUTE_NAME, edition);
            return true;
        } else {
            request.addAttributeToJsp(ERROR_EDITION_ATTRIBUTE, ERROR_EDITION_NOT_FOUND_MESSAGE);
            return false;
        }
    }

    private boolean addPriceToJSP (CommandRequest request) {
        final String editionId = request.getParameter(EDITION_ID_REQUEST_PARAM_NAME);
        final double priceValue = Double.parseDouble(request.getParameter(TERM_REQUEST_PARAM_NAME));
        final Optional<Edition> optionalEdition = editionService.findById(Long.parseLong(editionId));
        if (optionalEdition.isPresent()) {
            Edition edition = optionalEdition.get();
            if (edition.getThreeMonthsPrice().doubleValue() == priceValue) {
                final Price price = findPrice(edition.getId(), THREE_MONTHS_TERM_ID);
                final Term term = findTerm(THREE_MONTHS_TERM_ID);
                request.addAttributeToJsp(PRICE_ATTRIBUTE_NAME, price);
                request.addAttributeToJsp(TERM_ATTRIBUTE_NAME, term);
                return true;
            }
            if (edition.getSixMonthsPrice().doubleValue() == priceValue) {
                final Price price = findPrice(edition.getId(), SIX_MONTHS_TERM_ID);
                final Term term = findTerm(SIX_MONTHS_TERM_ID);
                request.addAttributeToJsp(PRICE_ATTRIBUTE_NAME, price);
                request.addAttributeToJsp(TERM_ATTRIBUTE_NAME, term);
                return true;
            }
            if (edition.getTwelveMonthsPrice().doubleValue() == priceValue) {
                final Price price = findPrice(edition.getId(), TWELVE_MONTHS_TERM_ID);
                final Term term = findTerm(TWELVE_MONTHS_TERM_ID);
                request.addAttributeToJsp(PRICE_ATTRIBUTE_NAME, price);
                request.addAttributeToJsp(TERM_ATTRIBUTE_NAME, term);
                return true;
            }
            return true;
        } else {
            request.addAttributeToJsp(ERROR_EDITION_ATTRIBUTE, ERROR_EDITION_NOT_FOUND_MESSAGE);
            return false;
        }
    }

    private Price findPrice (Long editionId, Long termId) {
        final Optional<Price> optionalPrice = priceService.findByEditionIdTermID(editionId, termId);
        return optionalPrice.orElse(null);
    }

    private Term findTerm (Long id) {
        final Optional<Term> optionalTerm = termService.findById(id);
        return optionalTerm.orElse(null);
    }

    private Long getAccountId(CommandRequest request) {
        if (request.retrieveFromSession(ACCOUNT_SESSION_ATTRIBUTE_NAME).isPresent()) {
            final Account account = (Account) request.retrieveFromSession(ACCOUNT_SESSION_ATTRIBUTE_NAME).get();
            return account.getId();
        } else {
            return null;
        }
    }
}
