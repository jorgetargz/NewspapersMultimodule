package domain.services.impl;

import dao.*;
import domain.common.Constantes;
import domain.services.ServicesRatings;
import domain.services.excepciones.ValidationException;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Log4j2
public class ServicesRatingsImpl implements ServicesRatings {

    private final RatingsDao daoRatings;
    private final ArticlesDao daoArticles;
    private final ReadersDao daoReaders;
    private final NewspapersDao daoNewspapers;
    private final SubscriptionsDao daoSubscriptions;

    @Inject
    public ServicesRatingsImpl(RatingsDao daoRatings, ArticlesDao daoArticles, ReadersDao daoReaders, NewspapersDao daoNewspapers, SubscriptionsDao daoSubscriptions) {
        this.daoRatings = daoRatings;
        this.daoArticles = daoArticles;
        this.daoReaders = daoReaders;
        this.daoNewspapers = daoNewspapers;
        this.daoSubscriptions = daoSubscriptions;
    }

    @Override
    public List<ArticleRating> getRatings() {
        return daoRatings.getAll();
    }

    @Override
    public List<ArticleRating> getRatingsByReaderId(String readerId) {
        try {
            return daoRatings.getAllByReaderId(Integer.parseInt(readerId));
        } catch (NumberFormatException e) {
            log.warn(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public ArticleRating getRating(String id) {
        try {
            return daoRatings.get(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            log.warn(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public ArticleRating updateRating(ArticleRating articleRating) {
        return daoRatings.update(articleRating);
    }

    @Override
    public ArticleRating saveRating(ArticleRating articleRating) {
        Reader reader = daoReaders.get(articleRating.getReaderId());
        Article article = daoArticles.get(articleRating.getArticleId());
        Newspaper newspaper = daoNewspapers.get(article.getNewspaperId());
        Subscription subscription = daoSubscriptions.get(newspaper.getId(), reader.getId());
        if (subscription.getCancellationDate() != null && subscription.getCancellationDate().isBefore(LocalDate.now().plus(1, ChronoUnit.DAYS))) {
            log.warn(Constantes.YOU_HAVE_CANCELED_YOUR_SUBSCRIPTION_TO_THIS_NEWSPAPER);
            throw new ValidationException(Constantes.YOU_HAVE_CANCELED_YOUR_SUBSCRIPTION_TO_THIS_NEWSPAPER);
        } else if (subscription.getSigningDate().isAfter(LocalDate.now())) {
            log.warn(Constantes.YOU_HAVE_NOT_YET_STARTED_YOUR_SUBSCRIPTION_TO_THIS_NEWSPAPER);
            throw new ValidationException(Constantes.YOU_HAVE_NOT_YET_STARTED_YOUR_SUBSCRIPTION_TO_THIS_NEWSPAPER);
        } else if (articleRating.getRating() < 0 || articleRating.getRating() > 10) {
            log.warn(Constantes.RATING_MUST_BE_BETWEEN_0_AND_10);
            throw new ValidationException(Constantes.RATING_MUST_BE_BETWEEN_0_AND_10);
        } else {
            return daoRatings.save(articleRating);
        }
    }

    @Override
    public void deleteRating(ArticleRating articleRating) {
        daoRatings.delete(articleRating);
    }

    @Override
    public void deleteAllRatingsByNewspaper(String newspaperId) {
        try {
            daoRatings.deleteAllByNewspaperId(Integer.parseInt(newspaperId));
        } catch (NumberFormatException e) {
            log.warn(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }
}
