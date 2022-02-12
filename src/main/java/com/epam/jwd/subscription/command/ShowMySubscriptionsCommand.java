package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.Account;
import com.epam.jwd.subscription.entity.Archive;
import com.epam.jwd.subscription.service.ArchiveService;
import com.epam.jwd.subscription.service.ServiceFactory;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ShowMySubscriptionsCommand implements Command {

    private static final String MY_ARCHIVE_DATA_PAGE = "page.my_archive";
    private static final String ACCOUNT_SESSION_ATTRIBUTE_NAME = "account";
    private static final String JSP_ARCHIVE_ATTRIBUTE_NAME = "archives";


    private static ShowMySubscriptionsCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final ArchiveService archiveService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private ShowMySubscriptionsCommand(ArchiveService archiveService,
                                       RequestFactory requestFactory, PropertyContext propertyContext) {
        this.archiveService = archiveService;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static ShowMySubscriptionsCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowMySubscriptionsCommand(ServiceFactory.getInstance().archiveService(),
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
        if (request.retrieveFromSession(ACCOUNT_SESSION_ATTRIBUTE_NAME).isPresent()) {
            Account account = (Account) request.retrieveFromSession(ACCOUNT_SESSION_ATTRIBUTE_NAME).get();
            final List<Archive> archives = archiveService.findByAccId(account.getId());
            request.addAttributeToJsp(JSP_ARCHIVE_ATTRIBUTE_NAME, archives);
        }
        return requestFactory.createForwardResponse(propertyContext.get(MY_ARCHIVE_DATA_PAGE));
    }
}
