package domain.services;

import modelo.ArticleRating;

import java.util.List;

public interface ServicesRatings {
    List<ArticleRating> getRatings();

    List<ArticleRating> getRatingsByReaderId(String readerId);

    ArticleRating getRating(String id);

    ArticleRating updateRating(ArticleRating articleRating);

    ArticleRating saveRating(ArticleRating articleRating);

    void deleteRating(ArticleRating articleRating);

    void deleteAllRatingsByNewspaper(String newspaperId);
}
