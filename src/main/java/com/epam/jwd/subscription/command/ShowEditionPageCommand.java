package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.entity.Edition;
import com.epam.jwd.subscription.service.EntityService;
import com.epam.jwd.subscription.service.ServiceFactory;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ShowEditionPageCommand implements Command {

    private static final String EDITIONS_ATTRIBUTE_NAME = "editions";

    private static ShowEditionPageCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();
    private final static CommandResponce FORWARD_TO_EDITIONS_PAGE = new CommandResponce() {
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

    private ShowEditionPageCommand(EntityService<Edition> service) {
        this.service = service;
    }

    public static ShowEditionPageCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowEditionPageCommand(ServiceFactory.instance().serviceFor(Edition.class));
                }
            } finally {
                LOCK.unlock();
            }
        }
        return instance;
    }

    @Override
    public CommandResponce execute(CommandRequest request) {
        final List<Edition> editions = service.findAll();
        request.addAttributeToJsp(EDITIONS_ATTRIBUTE_NAME, editions);
        return FORWARD_TO_EDITIONS_PAGE;
    }
}
