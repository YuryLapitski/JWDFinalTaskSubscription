package com.epam.jwd.subscription.db;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ConnectionPoolInitializeListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.transactionInstance().init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.transactionInstance().shutDown();
    }
}
