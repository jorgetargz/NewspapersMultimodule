package dao;

import modelo.ArticleType;

import java.util.List;

public interface ArticleTypesDao {
    List<ArticleType> getAll();

    ArticleType get(int id);

    ArticleType save(ArticleType articleType);

    ArticleType update(ArticleType articleType);

    void delete(ArticleType articleType);
}
