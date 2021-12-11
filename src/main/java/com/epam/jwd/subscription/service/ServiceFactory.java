package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.entity.*;

public interface ServiceFactory {

    <T extends Entity> EntityService<T> serviceFor(Class<T> entityClass);

    default AccountService accountService() {
        return (AccountService) serviceFor(Account.class);
    }

    default SimpleUserService userService() {
        return (SimpleUserService) serviceFor(User.class);
    }

    default SimpleEditionService editionService() {
        return (SimpleEditionService) serviceFor(Edition.class);
    }

    default SimplePriceService priceService() {
        return (SimplePriceService) serviceFor(Price.class);
    }

    default SimpleAddressService addressService() {
        return (SimpleAddressService) serviceFor(Address.class);
    }

    default TermService termService() {
        return (TermService) serviceFor(Term.class);
    }

    default StatusService statusService() {
        return (StatusService) serviceFor(Status.class);
    }

    default SimpleSubscriptionService subscriptionService() {
        return (SimpleSubscriptionService) serviceFor(Subscription.class);
    }

    default SimpleCardService cardService() {
        return (SimpleCardService) serviceFor(Card.class);
    }

    default SimpleArchiveService archiveService() {
        return (SimpleArchiveService) serviceFor(Archive.class);
    }

    static SimpleServiceFactory instance() {
        return SimpleServiceFactory.getInstance();
    }
}
