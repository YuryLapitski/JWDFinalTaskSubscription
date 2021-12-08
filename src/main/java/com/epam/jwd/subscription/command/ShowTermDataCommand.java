package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.Address;
import com.epam.jwd.subscription.entity.Edition;
import com.epam.jwd.subscription.service.AddressService;
import com.epam.jwd.subscription.service.SimpleEditionService;
import com.epam.jwd.subscription.service.ServiceFactory;
import com.epam.jwd.subscription.validator.AddressValidator;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class ShowTermDataCommand implements Command {

    private static final String CITY_REQUEST_PARAM_NAME = "city";
    private static final String STREET_REQUEST_PARAM_NAME = "street";
    private static final String HOUSE_REQUEST_PARAM_NAME = "house";
    private static final String FLAT_REQUEST_PARAM_NAME = "flat";
    private static final String EDITION_ID_REQUEST_PARAM_NAME = "editionId";
    private static final String EDITION_ATTRIBUTE_NAME = "edition";
    private static final String TERM_PAGE = "page.term";
    private static final String ADDRESS_PAGE = "page.address";
    private static final String ERROR_ADDRESS_ATTRIBUTE = "errorAddressMessage";
    private static final String ERROR_ADDRESS_MESSAGE = "Invalid address";
    private static final String ADDRESS_ATTRIBUTE_NAME = "address";
    private static final Integer DEFAULT_FLAT_NUMBER = 0;
    private static final String EMPTY_STRING = "";

    private static ShowTermDataCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final AddressService addressService;
    private final SimpleEditionService editionService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private ShowTermDataCommand(AddressService addressService,
                                SimpleEditionService editionService,
                                RequestFactory requestFactory,
                                PropertyContext propertyContext) {
        this.addressService = addressService;
        this.editionService = editionService;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static ShowTermDataCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowTermDataCommand(ServiceFactory.instance().addressService(),
                            ServiceFactory.instance().editionService(),
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
        final String editionId = request.getParameter(EDITION_ID_REQUEST_PARAM_NAME);
        Optional<Edition> optionalEdition = editionService.findById(Long.parseLong(editionId));
        if (optionalEdition.isPresent()) {
            Edition edition = optionalEdition.get();
            request.addAttributeToJsp(EDITION_ATTRIBUTE_NAME, edition);
        }
        final String city = request.getParameter(CITY_REQUEST_PARAM_NAME);
        final String street = request.getParameter(STREET_REQUEST_PARAM_NAME);
        final String house = request.getParameter(HOUSE_REQUEST_PARAM_NAME);
        Integer flat;
        if (request.getParameter(FLAT_REQUEST_PARAM_NAME).equals(EMPTY_STRING)) {
            flat = DEFAULT_FLAT_NUMBER;
        } else {
            flat = Integer.parseInt(request.getParameter(FLAT_REQUEST_PARAM_NAME));
        }
        if (AddressValidator.getInstance().validateAll(city, street, house)) {
            if (!addressService.findByCSHF(city, street, house, flat).isPresent()) {
                Address address = new Address(city, street, house, flat);
                addressService.create(address);
                Optional<Address> optionalAddress = addressService.findByCSHF(city, street, house, flat);
                if (optionalAddress.isPresent()) {
                    Address newAddress = optionalAddress.get();
                    request.addAttributeToJsp(ADDRESS_ATTRIBUTE_NAME, newAddress);
                    return requestFactory.createForwardResponse(propertyContext.get(TERM_PAGE));
                } else {
                    return null;
                }
            } else {
                Address address = addressService.findByCSHF(city, street, house, flat).get();
                request.addAttributeToJsp(ADDRESS_ATTRIBUTE_NAME, address);
                return requestFactory.createForwardResponse(propertyContext.get(TERM_PAGE));
            }
        } else {
            request.addAttributeToJsp(ERROR_ADDRESS_ATTRIBUTE, ERROR_ADDRESS_MESSAGE);
            return requestFactory.createForwardResponse(propertyContext.get(ADDRESS_PAGE));
        }
    }
}
