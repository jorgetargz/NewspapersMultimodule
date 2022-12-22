package dao.jdbc_impl;

import dao.DBConnection;
import dao.RatingsDao;
import dao.common.Constantes;
import dao.excepciones.DatabaseException;
import dao.excepciones.NotFoundException;
import dao.utils.SQLQueries;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.ArticleRating;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class RatingsDaoImpl implements RatingsDao {

    private final DBConnection dbConnection;

    @Inject
    public RatingsDaoImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<ArticleRating> getAll() {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_RATINGS_QUERY)) {
            ResultSet rs = preparedStatement.executeQuery();
            List<ArticleRating> list = getArticleRatingsFromRS(rs);
            if (list.isEmpty()) {
                log.info(Constantes.THERE_ARE_NO_RATINGS_IN_THE_DATABASE);
                throw new NotFoundException(Constantes.THERE_ARE_NO_RATINGS_IN_THE_DATABASE);
            } else {
                return list;
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public List<ArticleRating> getAllByReaderId(int readerid) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_RATINGS_BY_READER_QUERY)) {
            preparedStatement.setInt(1, readerid);
            ResultSet rs = preparedStatement.executeQuery();
            List<ArticleRating> list = getArticleRatingsFromRS(rs);
            if (list.isEmpty()) {
                log.info(Constantes.NO_RATINGS_MADE_BY_THIS_READER);
                throw new NotFoundException(Constantes.NO_RATINGS_MADE_BY_THIS_READER);
            } else {
                return list;
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public ArticleRating get(int id) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_RATING_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                ArticleRating articleRating = new ArticleRating();
                articleRating.setId(rs.getInt(Constantes.ID));
                articleRating.setRating(rs.getInt(Constantes.RATING));
                articleRating.setArticleId(rs.getInt(Constantes.ID_ARTICLE));
                articleRating.setReaderId(rs.getInt(Constantes.ID_READER));
                return articleRating;
            } else {
                log.info(Constantes.NO_RATING_WITH_THIS_ID_IN_THE_DATABASE);
                throw new NotFoundException(Constantes.NO_RATING_WITH_THIS_ID_IN_THE_DATABASE);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public ArticleRating save(ArticleRating articleRating) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_RATING_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, articleRating.getArticleId());
            preparedStatement.setInt(2, articleRating.getReaderId());
            preparedStatement.setInt(3, articleRating.getRating());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                articleRating.setId(rs.getInt(1));
            }
            return articleRating;
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public ArticleRating update(ArticleRating articleRating) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_RATING_QUERY)) {
            preparedStatement.setInt(1, articleRating.getRating());
            preparedStatement.setInt(2, articleRating.getArticleId());
            preparedStatement.setInt(3, articleRating.getReaderId());
            preparedStatement.executeUpdate();
            return articleRating;
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public void delete(ArticleRating articleRating) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_RATING_QUERY)) {
            preparedStatement.setInt(1, articleRating.getArticleId());
            preparedStatement.setInt(2, articleRating.getReaderId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public void deleteAllByNewspaperId(int newspaperId) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_RATINGS_BY_NEWSPAPER_QUERY)) {
            preparedStatement.setInt(1, newspaperId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    private List<ArticleRating> getArticleRatingsFromRS(ResultSet rs) throws SQLException {
        List<ArticleRating> ratings = new ArrayList<>();
        while (rs.next()) {
            ArticleRating articleRating = new ArticleRating();
            articleRating.setId(rs.getInt(Constantes.ID));
            articleRating.setArticleId(rs.getInt(Constantes.ID_ARTICLE));
            articleRating.setReaderId(rs.getInt(Constantes.ID_READER));
            articleRating.setRating(rs.getInt(Constantes.RATING));
            ratings.add(articleRating);
        }
        return ratings;
    }
}
