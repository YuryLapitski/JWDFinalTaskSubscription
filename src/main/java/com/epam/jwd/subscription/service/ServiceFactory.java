package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.entity.*;

public interface ServiceFactory {

    <T extends Entity> EntityService<T> serviceFor(Class<T> entityClass);

    default AccountService accountService() {
        return (AccountService) serviceFor(Account.class);
    }

    default UserService userService() {
        return (UserService) serviceFor(User.class);
    }

    default EditionService editionService() {
        return (EditionService) serviceFor(Edition.class);
    }

    default SimplePriceService priceService() {
        return (SimplePriceService) serviceFor(Price.class);
    }

    default AddressService addressService() {
        return (AddressService) serviceFor(Address.class);
    }

    default TermService termService() {
        return (TermService) serviceFor(Term.class);
    }

    default StatusService statusService() {
        return (StatusService) serviceFor(Status.class);
    }

    default SubscriptionService subscriptionService() {
        return (SubscriptionService) serviceFor(Subscription.class);
    }

    static SimpleServiceFactory instance() {
        return SimpleServiceFactory.getInstance();
    }
}
