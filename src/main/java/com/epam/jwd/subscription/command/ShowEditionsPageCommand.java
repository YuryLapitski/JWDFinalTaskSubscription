package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.Edition;
import com.epam.jwd.subscription.service.EntityService;
import com.epam.jwd.subscription.service.ServiceFactory;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ShowEditionsPageCommand implements Command {

    private static final String EDITIONS_ATTRIBUTE_NAME = "editions";
    private static final String EDITIONS_PAGE = "page.editions";

    private static ShowEditionsPageCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final EntityService<Edition> service;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private ShowEditionsPageCommand(EntityService<Edition> service, RequestFactory requestFactory,
                                    PropertyContext propertyContext) {
        this.service = service;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static ShowEditionsPageCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowEditionsPageCommand(ServiceFactory.instance().serviceFor(Edition.class),
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
        final List<Edition> editions = service.findAll();
        request.addAttributeToJsp(EDITIONS_ATTRIBUTE_NAME, editions);
        return requestFactory.createForwardResponse(propertyContext.get(EDITIONS_PAGE));
    }
}
