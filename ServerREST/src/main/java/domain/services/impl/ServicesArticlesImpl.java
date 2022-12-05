package domain.services.impl;

import dao.ArticleTypesDao;
import dao.ArticlesDao;
import dao.NewspapersDao;
import domain.common.Constantes;
import domain.services.ServicesArticles;
import domain.services.excepciones.ValidationException;
import jakarta.inject.Inject;
import modelo.*;

import java.util.List;


public class ServicesArticlesImpl implements ServicesArticles {

    private final ArticlesDao daoArticles;
    private final NewspapersDao daoNewspapers;
    private final ArticleTypesDao daoArticleTypes;

    @Inject
    public ServicesArticlesImpl(ArticlesDao daoArticles, NewspapersDao daoNewspapers, ArticleTypesDao daoArticleTypes) {
        this.daoArticles = daoArticles;
        this.daoNewspapers = daoNewspapers;
        this.daoArticleTypes = daoArticleTypes;
    }

    @Override
    public List<Article> getArticles() {
        return daoArticles.getAll();
    }

    @Override
    public List<Article> getArticlesByType(String typeId) {
        try {
            return daoArticles.getAllByArticleTypeId(Integer.parseInt(typeId));
        } catch (NumberFormatException e) {
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public List<Article> getArticlesByNewspaper(String idNewspaper) {
        try {
            return daoArticles.getAllByNewspaperId(Integer.parseInt(idNewspaper));
        } catch (NumberFormatException e) {
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public List<Article> getArticlesAvailableForReader(String idReader) {
        try {
            return daoArticles.getAllByReaderId(Integer.parseInt(idReader));
        } catch (NumberFormatException e) {
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public List<ArticleType> getArticleTypes() {
        return daoArticleTypes.getAll();
    }

    @Override
    public Article getArticle(String id) {
        try {
            return daoArticles.get(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public ArticleType getArticleType(String id) {
        try {
            return daoArticleTypes.get(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public Article saveArticle(Article article) {
        if (daoNewspapers.get(article.getNewspaperId()) != null
                && daoArticleTypes.get(article.getArticleType().getId()) != null) {
            return daoArticles.save(article);
        } else {
            return null;
        }
    }

    @Override
    public ArticleType saveArticleType(ArticleType articleType) {
        return daoArticleTypes.save(articleType);
    }

    @Override
    public Article updateArticle(Article article) {
        if (daoNewspapers.get(article.getNewspaperId()) != null
                && daoArticleTypes.get(article.getArticleType().getId()) != null) {
            return daoArticles.update(article);
        } else {
            return null;
        }
    }

    @Override
    public ArticleType updateArticleType(ArticleType articleType) {
        return daoArticleTypes.update(articleType);
    }

    @Override
    public void deleteArticle(Article article) {
        daoArticles.delete(article);
    }

    @Override
    public void deleteArticleType(ArticleType articleType) {
        daoArticleTypes.delete(articleType);
    }

    @Override
    public ArticleQuery1 getArticleInfo(String id) {
        try {
            return daoArticles.getArticleQuery1(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public List<ArticleQuery2> getArticlesByTypeWithNewspaper(String articleTypeId) {
        try {
            return daoArticles.getArticlesQuery2(Integer.parseInt(articleTypeId));
        } catch (NumberFormatException e) {
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public List<ArticleQuery3> getArticlesByNewspaperWithBadRatings(String newspaperId) {
        try {
            return daoArticles.getArticlesQuery3(Integer.parseInt(newspaperId));
        } catch (NumberFormatException e) {
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }
}

