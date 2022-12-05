package dao.impl;

import dao.ArticleTypesDao;
import dao.DBConnection;
import dao.common.Constantes;
import dao.excepciones.DatabaseException;
import dao.utils.SQLQueries;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.ArticleType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Log4j2
public class ArticleTypesDaoImpl implements ArticleTypesDao {

    private final DBConnection dbConnection;

    @Inject
    public ArticleTypesDaoImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<ArticleType> getAll() {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            List<ArticleType> list = jdbcTemplate.query(SQLQueries.SELECT_ARTICLE_TYPES_QUERY, new BeanPropertyRowMapper<>(ArticleType.class));
            if (list.isEmpty()) {
                log.error(Constantes.NO_ARTICLE_TYPES_FOUND);
                throw new DatabaseException(Constantes.NO_ARTICLE_TYPES_FOUND);
            } else {
                return list;
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public ArticleType get(int id) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            List<ArticleType> list = jdbcTemplate.query(SQLQueries.SELECT_ARTICLE_TYPE_BY_ID_QUERY, new BeanPropertyRowMapper<>(ArticleType.class), id);
            if (list.isEmpty()) {
                log.error(Constantes.ARTICLE_TYPE_NOT_FOUND);
                throw new DatabaseException(Constantes.ARTICLE_TYPE_NOT_FOUND);
            } else {
                return list.get(0);
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public ArticleType save(ArticleType articleType) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(SQLQueries.INSERT_ARTICLE_TYPE_QUERY,
                                Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, articleType.getDescription());
                return ps;
            }, keyHolder);
            if (rowsAffected == 0) {
                log.info(Constantes.ARTICLE_TYPE_NOT_SAVED);
                throw new DatabaseException(Constantes.ARTICLE_TYPE_NOT_SAVED);
            } else {
                articleType.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
                return articleType;
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public ArticleType update(ArticleType articleType) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            if (jdbcTemplate.update(SQLQueries.UPDATE_ARTICLE_TYPE_QUERY, articleType.getDescription(), articleType.getId()) == 0) {
                log.error(Constantes.ARTICLE_TYPE_NOT_UPDATED);
                throw new DatabaseException(Constantes.ARTICLE_TYPE_NOT_UPDATED);
            } else {
                return articleType;
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void delete(ArticleType articleType) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            if (jdbcTemplate.update(SQLQueries.DELETE_ARTICLE_TYPE_QUERY, articleType.getId()) == 0) {
                log.error(Constantes.ARTICLE_TYPE_NOT_DELETED);
                throw new DatabaseException(Constantes.ARTICLE_TYPE_NOT_DELETED);
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
