package dao;

import modelo.Newspaper;
import modelo.Subscription;

import java.util.List;

public interface SubscriptionsDao {
    List<Subscription> getAll();

    List<Subscription> getAllByNewspaper(int newspaperId);

    List<Subscription> getOldestSubscriptionsByNewspaper(int newspaperId);

    List<Subscription> getAllByReader(int readerId);

    Subscription get(int newspaperId, int readerId);

    Subscription save(Subscription subscription);

    Subscription update(Subscription subscription);

    void delete(Subscription subscription);

    void deleteAll(Newspaper newspaper);
}
