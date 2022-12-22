package dao.jdbc_impl;

import dao.DBConnection;
import dao.NewspapersDao;
import dao.common.Constantes;
import dao.excepciones.DatabaseException;
import dao.excepciones.NotFoundException;
import dao.jdbc_impl.row_mapers.NewspaperRowMapper;
import dao.utils.SQLQueries;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.Newspaper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Log4j2
public class NewspapersDaoImpl implements NewspapersDao {

    private final DBConnection dbConnection;

    @Inject
    public NewspapersDaoImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Newspaper> getAll() {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            List<Newspaper> list = jdbcTemplate.query(SQLQueries.SELECT_NEWSPAPERS_QUERY, new NewspaperRowMapper());
            if (list.isEmpty()) {
                log.error(Constantes.NO_NEWSPAPERS_FOUND);
                throw new NotFoundException(Constantes.NO_NEWSPAPERS_FOUND);
            } else {
                return list;
            }
        } catch (DataAccessException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public Newspaper get(int id) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            List<Newspaper> list = jdbcTemplate.query(SQLQueries.SELECT_NEWSPAPER_BY_ID_QUERY, new NewspaperRowMapper(), id);
            if (list.isEmpty()) {
                log.error(Constantes.NEWSPAPER_NOT_FOUND);
                throw new NotFoundException(Constantes.NEWSPAPER_NOT_FOUND);
            } else {
                return list.get(0);
            }
        } catch (DataAccessException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }

    }

    @Override
    public Newspaper save(Newspaper newspaper) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(SQLQueries.INSERT_NEWSPAPER_QUERY,
                                Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, newspaper.getNameNewspaper());
                ps.setDate(2, Date.valueOf(newspaper.getReleaseDate()));
                return ps;
            }, keyHolder);
            if (rowsAffected == 0) {
                log.info(Constantes.NEWSPAPER_NOT_SAVED);
                throw new NotFoundException(Constantes.NEWSPAPER_NOT_SAVED);
            } else {
                newspaper.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
                return newspaper;
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public Newspaper update(Newspaper newspaper) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
            if (jdbcTemplate.update(SQLQueries.UPDATE_NEWSPAPER_QUERY, newspaper.getNameNewspaper(), newspaper.getReleaseDate(), newspaper.getId()) == 0) {
                log.error(Constantes.NEWSPAPER_NOT_UPDATED);
                throw new DatabaseException(Constantes.NEWSPAPER_NOT_UPDATED);
            } else {
                return newspaper;
            }
        } catch (DataAccessException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public void delete(Newspaper newspaper) {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnection.getDataSource());
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        try {
            JdbcTemplate jtm = new JdbcTemplate(dbConnection.getDataSource());
            jtm.update(SQLQueries.DELETE_RATINGS_BY_NEWSPAPER_QUERY, newspaper.getId());
            jtm.update(SQLQueries.DELETE_SUBSCRIPTIONS_BY_NEWSPAPER, newspaper.getId());
            jtm.update(SQLQueries.DELETE_ARTICLES_BY_NEWSPAPER_QUERY, newspaper.getId());
            int rowsAffected = jtm.update(SQLQueries.DELETE_NEWSPAPER_QUERY, newspaper.getId());
            if (rowsAffected == 0) {
                throw new NotFoundException(Constantes.NEWSPAPER_NOT_FOUND);
            }
            transactionManager.commit(transactionStatus);
        } catch (DataAccessException e) {
            transactionManager.rollback(transactionStatus);
            log.error(e.getMessage(), e);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }
}
