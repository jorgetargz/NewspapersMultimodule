package domain.services;

import modelo.*;

import java.util.List;

public interface ServicesArticles {
    List<Article> getArticles();

    List<Article> getArticlesByType(String typeId);

    List<ArticleQuery2> getArticlesByTypeWithNewspaper(String articleTypeId);

    List<Article> getArticlesByNewspaper(String idNewspaper);

    List<ArticleQuery3> getArticlesByNewspaperWithBadRatings(String newspaperId);

    List<Article> getArticlesAvailableForReader(String idReader);

    List<ArticleType> getArticleTypes();

    Article getArticle(String id);

    ArticleQuery1 getArticleInfo(String id);

    ArticleType getArticleType(String id);

    Article saveArticle(Article article);

    ArticleType saveArticleType(ArticleType articleType);

    Article updateArticle(Article article);

    ArticleType updateArticleType(ArticleType articleType);

    void deleteArticle(Article article);

    void deleteArticleType(ArticleType articleType);
}