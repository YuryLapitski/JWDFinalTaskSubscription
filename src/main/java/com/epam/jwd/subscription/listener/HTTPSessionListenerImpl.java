package com.epam.jwd.subscription.listener;

import com.epam.jwd.subscription.entity.Subscription;
import com.epam.jwd.subscription.service.ServiceFactory;
import com.epam.jwd.subscription.service.SubscriptionService;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class HTTPSessionListenerImpl implements HttpSessionListener {

    private static final String SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME = "subscriptions";
    private static final int TIMEOUT = 600; // 10 minutes

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setMaxInactiveInterval(TIMEOUT);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        final List<Subscription> subscriptions =
                (ArrayList<Subscription>) session.getAttribute(SUBSCRIPTIONS_SESSION_ATTRIBUTE_NAME);
        SubscriptionService subscriptionService = ServiceFactory.instance().subscriptionService();
        if (subscriptions != null) {
            subscriptionService.deleteAllSubscriptions(subscriptions);
        }
        session.invalidate();
    }

}
