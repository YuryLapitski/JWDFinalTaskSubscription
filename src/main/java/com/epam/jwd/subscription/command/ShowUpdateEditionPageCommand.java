package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.Edition;
import com.epam.jwd.subscription.service.SimpleEditionService;
import com.epam.jwd.subscription.service.ServiceFactory;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class ShowUpdateEditionPageCommand implements Command {

    private static final String EDITION_ATTRIBUTE_NAME = "edition";
    private static final String UPDATE_EDITIONS_PAGE = "page.update_edition";
    private static final String EDITION_ID_REQUEST_PARAM_NAME = "editionId";

    private static ShowUpdateEditionPageCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final SimpleEditionService editionService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private ShowUpdateEditionPageCommand(SimpleEditionService editionService, RequestFactory requestFactory,
                                         PropertyContext propertyContext) {
        this.editionService = editionService;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static ShowUpdateEditionPageCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowUpdateEditionPageCommand(ServiceFactory.instance().editionService(),
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
        final Long editionId = Long.parseLong(request.getParameter(EDITION_ID_REQUEST_PARAM_NAME));
        Optional<Edition> optionalEdition = editionService.findById(editionId);
        if (optionalEdition.isPresent()) {
            Edition edition = optionalEdition.get();
            request.addAttributeToJsp(EDITION_ATTRIBUTE_NAME, edition);
            return requestFactory.createForwardResponse(propertyContext.get(UPDATE_EDITIONS_PAGE));
        }
        return null;
    }
}
