package com.epam.jwd.subscription.command;

import java.util.concurrent.locks.ReentrantLock;

public class ShowMainPageCommand implements Command {

    private static ShowMainPageCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private ShowMainPageCommand() {
    }

    public static ShowMainPageCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowMainPageCommand();
                }
            } finally {
                LOCK.unlock();
            }
        }
        return instance;
    }

    private final static CommandResponse FORWARD_TO_MAIN_PAGE_RESPONCE = new CommandResponse() {
        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public String getPath() {
            return "WEB-INF/jsp/main.jsp";
        }
    };

    @Override
    public CommandResponse execute(CommandRequest request) {
        return FORWARD_TO_MAIN_PAGE_RESPONCE;
    }
}
