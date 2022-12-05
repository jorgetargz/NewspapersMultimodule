package dao;

import modelo.ArticleRating;

import java.util.List;

public interface RatingsDao {
    List<ArticleRating> getAll();

    List<ArticleRating> getAllByReaderId(int readerId);

    ArticleRating get(int id);

    ArticleRating save(ArticleRating articleRating);

    ArticleRating update(ArticleRating articleRating);

    void delete(ArticleRating articleRating);

    void deleteAllByNewspaperId(int newspaperId);
}
