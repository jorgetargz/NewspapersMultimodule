package dao.jdbc_impl;

import dao.ArticlesDao;
import dao.DBConnection;
import dao.common.Constantes;
import dao.excepciones.DatabaseException;
import dao.excepciones.NotFoundException;
import dao.jdbc_impl.row_mapers.ArticleQuery2RowMapper;
import dao.jdbc_impl.row_mapers.ArticleQuery3RowMapper;
import dao.jdbc_impl.row_mapers.ArticleRowMapper;
import dao.utils.SQLQueries;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.List;
import java.util.Objects;

@Log4j2
public class ArticlesDaoImpl implements ArticlesDao {

    private final DBConnection dbConnection;

    @Inject
    public ArticlesDaoImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Article> getAll() {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            List<Article> list = jdbcTemplate.query(SQLQueries.SELECT_ARTICLES_WITH_TYPE_QUERY, new ArticleRowMapper());
            if (list.isEmpty()) {
                log.info(Constantes.THERE_ARE_NO_ARTICLES);
                throw new NotFoundException(Constantes.THERE_ARE_NO_ARTICLES);
            } else {
                return list;
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public List<Article> getAllByNewspaperId(int newspaperId) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            List<Article> list = jdbcTemplate.query(SQLQueries.SELECT_ARTICLES_BY_NEWSPAPER_WITH_TYPE_QUERY, new ArticleRowMapper(), newspaperId);
            if (list.isEmpty()) {
                log.info(Constantes.NO_ARTICLES_OF_THIS_NEWSPAPER);
                throw new NotFoundException(Constantes.NO_ARTICLES_OF_THIS_NEWSPAPER);
            } else {
                return list;
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public List<Article> getAllByArticleTypeId(int articleTypeId) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            List<Article> list = jdbcTemplate.query(SQLQueries.SELECT_ARTICLES_BY_TYPE_WITH_TYPE_QUERY, new ArticleRowMapper(), articleTypeId);
            if (list.isEmpty()) {
                log.info(Constantes.NO_ARTICLES_OF_THIS_TYPE);
                throw new NotFoundException(Constantes.NO_ARTICLES_OF_THIS_TYPE);
            } else {
                return list;
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public List<Article> getAllByReaderId(int readerId) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            List<Article> list = jdbcTemplate.query(SQLQueries.SELECT_ARTICLES_BY_READER_WITH_TYPE_FROM_SUSCRIBE_QUERY, new ArticleRowMapper(), readerId);
            if (list.isEmpty()) {
                log.info(Constantes.NO_ARTICLES_OF_THIS_READER);
                throw new NotFoundException(Constantes.NO_ARTICLES_OF_THIS_READER);
            } else {
                return list;
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public Article get(int id) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            List<Article> list = jdbcTemplate.query(SQLQueries.SELECT_ARTICLE_BY_ID_WITH_TYPE_QUERY, new ArticleRowMapper(), id);
            if (list.isEmpty()) {
                log.info(Constantes.NO_ARTICLE_WITH_THIS_ID);
                throw new NotFoundException(Constantes.NO_ARTICLE_WITH_THIS_ID);
            } else {
                return list.get(0);
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public Article save(Article article) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(SQLQueries.INSERT_ARTICLE_QUERY,
                                Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, article.getName());
                ps.setInt(2, article.getArticleType().getId());
                ps.setInt(3, article.getNewspaperId());
                return ps;
            }, keyHolder);
            if (rowsAffected == 0) {
                log.info(Constantes.ARTICLE_COULD_NOT_BE_SAVED);
                throw new DatabaseException(Constantes.ARTICLE_COULD_NOT_BE_SAVED);
            } else {
                article.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
                return article;
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public Article update(Article article) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            if (jdbcTemplate.update(SQLQueries.UPDATE_ARTICLE_QUERY, article.getName(), article.getArticleType().getId(), article.getNewspaperId(), article.getId()) == 0) {
                log.info(Constantes.NO_ARTICLE_WITH_THIS_ID);
                throw new NotFoundException(Constantes.NO_ARTICLE_WITH_THIS_ID);
            } else {
                return article;
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public void delete(Article article) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            if (jdbcTemplate.update(SQLQueries.DELETE_ARTICLE_QUERY, article.getId()) == 0) {
                log.info(Constantes.NO_ARTICLE_WITH_THIS_ID);
                throw new NotFoundException(Constantes.NO_ARTICLE_WITH_THIS_ID);
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public void deleteAll(Newspaper newspaper) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            if (jdbcTemplate.update(SQLQueries.DELETE_ARTICLES_BY_NEWSPAPER_QUERY, newspaper.getId()) == 0) {
                log.info(Constantes.NO_ARTICLES_OF_THIS_NEWSPAPER);
                throw new NotFoundException(Constantes.NO_ARTICLES_OF_THIS_NEWSPAPER);
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public ArticleQuery1 getArticleQuery1(int id) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_ARTICLE_QUERY)) {

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new ArticleQuery1(
                        rs.getInt(Constantes.ID),
                        rs.getString(Constantes.NAME_ARTICLE),
                        rs.getString(Constantes.DESCRIPTION),
                        rs.getInt(Constantes.READERS)
                );
            } else {
                log.info(Constantes.NO_ARTICLE_WITH_THIS_ID);
                throw new NotFoundException(Constantes.NO_ARTICLE_WITH_THIS_ID);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public List<ArticleQuery2> getArticlesQuery2(int articleTypeId) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            List<ArticleQuery2> list = jdbcTemplate.query(SQLQueries.SELECT_ARTICLES_BY_TYPE_WITH_NEWSPAPER_QUERY, new ArticleQuery2RowMapper(), articleTypeId);
            if (list.isEmpty()) {
                log.info(Constantes.NO_ARTICLES_OF_THIS_TYPE);
                throw new NotFoundException(Constantes.NO_ARTICLES_OF_THIS_TYPE);
            } else {
                return list;
            }
        } catch (DataAccessException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public List<ArticleQuery3> getArticlesQuery3(int newspaperId) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            List<ArticleQuery3> list = jdbcTemplate.query(SQLQueries.SELECT_ARTICLES_BY_NEWSPAPER_WITH_BAD_RATING_QUERY, new ArticleQuery3RowMapper(), newspaperId);
            if (list.isEmpty()) {
                log.info(Constantes.NO_ARTICLES_OF_THIS_NEWSPAPER);
                throw new NotFoundException(Constantes.NO_ARTICLES_OF_THIS_NEWSPAPER);
            } else {
                return list;
            }
        } catch (DataAccessException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }
}
