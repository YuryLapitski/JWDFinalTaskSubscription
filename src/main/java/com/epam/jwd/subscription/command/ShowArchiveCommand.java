package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.Archive;
import com.epam.jwd.subscription.service.ArchiveService;
import com.epam.jwd.subscription.service.ServiceFactory;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ShowArchiveCommand implements Command {

    private static final String ARCHIVE_DATA_PAGE = "page.archive";
    private static final String JSP_ARCHIVE_ATTRIBUTE_NAME = "archives";

    private static ShowArchiveCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final ArchiveService archiveService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private ShowArchiveCommand(ArchiveService archiveService,
                                       RequestFactory requestFactory, PropertyContext propertyContext) {
        this.archiveService = archiveService;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static ShowArchiveCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowArchiveCommand(ServiceFactory.instance().archiveService(),
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
        final List<Archive> archives = archiveService.findAll();
        request.addAttributeToJsp(JSP_ARCHIVE_ATTRIBUTE_NAME, archives);
        return requestFactory.createForwardResponse(propertyContext.get(ARCHIVE_DATA_PAGE));
    }
}
