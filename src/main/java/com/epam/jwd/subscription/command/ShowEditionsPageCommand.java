package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.Edition;
import com.epam.jwd.subscription.service.EditionService;
import com.epam.jwd.subscription.service.ServiceFactory;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ShowEditionsPageCommand implements Command {

    private static final String EDITIONS_ATTRIBUTE_NAME = "editions";
    private static final String EDITIONS_PAGE = "page.editions";

    private static ShowEditionsPageCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final EditionService editionService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private ShowEditionsPageCommand(EditionService editionService, RequestFactory requestFactory,
                                    PropertyContext propertyContext) {
        this.editionService = editionService;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static ShowEditionsPageCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowEditionsPageCommand(ServiceFactory.getInstance().editionService(),
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
        final List<Edition> editions = editionService.findAll();
        request.addAttributeToJsp(EDITIONS_ATTRIBUTE_NAME, editions);
        return requestFactory.createForwardResponse(propertyContext.get(EDITIONS_PAGE));
    }
}
