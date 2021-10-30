package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.entity.Edition;
import com.epam.jwd.subscription.service.EntityService;
import com.epam.jwd.subscription.service.ServiceFactory;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ShowEditionsPageCommand implements Command {

    private static final String EDITIONS_ATTRIBUTE_NAME = "editions";

    private static ShowEditionsPageCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();
    private final static CommandResponse FORWARD_TO_EDITIONS_PAGE = new CommandResponse() {
        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public String getPath() {
            return "WEB-INF/jsp/editions.jsp";
        }
    };
    private final EntityService<Edition> service;

    private ShowEditionsPageCommand(EntityService<Edition> service) {
        this.service = service;
    }

    public static ShowEditionsPageCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowEditionsPageCommand(ServiceFactory.instance().serviceFor(Edition.class));
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
        return FORWARD_TO_EDITIONS_PAGE;
    }
}
