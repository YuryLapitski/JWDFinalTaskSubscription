package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.Edition;
import com.epam.jwd.subscription.service.*;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class DeleteEditionCommand implements Command {

    private static final String EDITIONS_ATTRIBUTE_NAME = "editions";
    private static final String EDITIONS_PAGE = "page.editions";
    private static final String EDITION_ID_REQUEST_PARAM_NAME = "editionId";

    private static DeleteEditionCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final SimpleEditionService editionService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private DeleteEditionCommand(SimpleEditionService editionService, RequestFactory requestFactory,
                                 PropertyContext propertyContext) {
        this.editionService = editionService;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static DeleteEditionCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new DeleteEditionCommand(ServiceFactory.instance().editionService(),
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
        editionService.delete(editionId);
        final List<Edition> editions = editionService.findAll();
        request.addAttributeToJsp(EDITIONS_ATTRIBUTE_NAME, editions);
        return requestFactory.createForwardResponse(propertyContext.get(EDITIONS_PAGE));
    }
}
