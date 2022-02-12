package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.Edition;
import com.epam.jwd.subscription.service.EditionService;
import com.epam.jwd.subscription.service.ServiceFactory;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class ShowAddressDataCommand implements Command {

    private static final String ADDRESS_PAGE = "page.address";
    private static final String EDITION_ATTRIBUTE_NAME = "edition";
    private static final String EDITION_ID_REQUEST_PARAM_NAME = "editionId";

    private static ShowAddressDataCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final EditionService editionService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private ShowAddressDataCommand(EditionService editionService,
                                   RequestFactory requestFactory,
                                   PropertyContext propertyContext) {
        this.editionService = editionService;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static ShowAddressDataCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowAddressDataCommand(
                            ServiceFactory.getInstance().editionService(),
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
        final String editionId = request.getParameter(EDITION_ID_REQUEST_PARAM_NAME);
        Optional<Edition> optionalEdition = editionService.findById(Long.parseLong(editionId));
        if (optionalEdition.isPresent()) {
            Edition edition = optionalEdition.get();
            request.addAttributeToJsp(EDITION_ATTRIBUTE_NAME, edition);
            return requestFactory.createForwardResponse(propertyContext.get(ADDRESS_PAGE));
        } else {
            return null;
        }
    }
}
