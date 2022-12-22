package domain.services.impl;

import dao.SubscriptionsDao;
import domain.common.Constantes;
import domain.services.ServicesSubscriptions;
import domain.services.excepciones.ValidationException;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.Subscription;

import java.util.List;

@Log4j2
public class ServicesSubscriptionsImpl implements ServicesSubscriptions {

    private final SubscriptionsDao daoSubscriptions;

    @Inject
    public ServicesSubscriptionsImpl(SubscriptionsDao daoSubscriptions) {
        this.daoSubscriptions = daoSubscriptions;
    }

    @Override
    public List<Subscription> getSubscriptions() {
        return daoSubscriptions.getAll();
    }


    @Override
    public List<Subscription> getSubscriptionsByNewspaper(String newspaperId) {
        try {
            return daoSubscriptions.getAllByNewspaper(Integer.parseInt(newspaperId));
        } catch (NumberFormatException e) {
            log.warn(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public List<Subscription> getOldestSubscriptionsByNewspaper(String newspaperId) {
        try {
            return daoSubscriptions.getOldestSubscriptionsByNewspaper(Integer.parseInt(newspaperId));
        } catch (NumberFormatException e) {
            log.warn(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public List<Subscription> getSubscriptionsByReader(String readerId) {
        try {
            return daoSubscriptions.getAllByReader(Integer.parseInt(readerId));
        } catch (NumberFormatException e) {
            log.warn(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public Subscription getSubscription(String idReader, String idNewspaper) {
        try {
            return daoSubscriptions.get(Integer.parseInt(idNewspaper), Integer.parseInt(idReader));
        } catch (NumberFormatException e) {
            log.warn(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public Subscription addSubscription(Subscription subscription) {
        return daoSubscriptions.save(subscription);
    }

    @Override
    public Subscription cancelSubscription(Subscription subscription) {
        return daoSubscriptions.update(subscription);
    }

    @Override
    public void deleteSubscription(Subscription subscription) {
        daoSubscriptions.delete(subscription);
    }
}
