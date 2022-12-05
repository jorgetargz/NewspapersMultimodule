package domain.services;

import modelo.Subscription;

import java.util.List;

public interface ServicesSubscriptions {
    List<Subscription> getSubscriptions();

    List<Subscription> getSubscriptionsByNewspaper(String newspaperId);

    List<Subscription> getOldestSubscriptionsByNewspaper(String newspaperId);

    List<Subscription> getSubscriptionsByReader(String readerId);

    Subscription getSubscription(String idReader, String idNewspaper);

    Subscription addSubscription(Subscription subscription);

    Subscription cancelSubscription(Subscription subscription);

    void deleteSubscription(Subscription subscription);
}
