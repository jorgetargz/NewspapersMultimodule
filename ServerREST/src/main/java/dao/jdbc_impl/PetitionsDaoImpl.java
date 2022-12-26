package dao.jdbc_impl;

import dao.DBConnection;
import dao.PetitionsDao;
import dao.common.Constantes;
import dao.excepciones.DatabaseException;
import dao.utils.SQLQueries;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.Petition;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class PetitionsDaoImpl implements PetitionsDao {

    private final DBConnection dbConnection;

    @Inject
    public PetitionsDaoImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Petition savePetition(Petition petition) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.INSERT_PETITION_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, petition.getUsername());
            preparedStatement.setString(2, petition.getTime().toString());
            preparedStatement.setString(3, petition.getPath());
            preparedStatement.setString(4, petition.getMethod());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                petition.setId(rs.getInt(1));
            }
            return petition;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    @Override
    public List<Petition> getPetitionsByUsernameAndPeriod(String username, Duration duration) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_PETITIONS_BY_USERNAME_AND_TIME_QUERY)) {

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime periodStart = now.minus(duration);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, periodStart.toString());
            ResultSet rs = preparedStatement.executeQuery();
            return getPetitionsFromResultSet(rs);

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
    }

    private List<Petition> getPetitionsFromResultSet(ResultSet rs) throws SQLException {
        List<Petition> petitions = new ArrayList<>();
        while (rs.next()) {
            petitions.add(getPetitionFromRow(rs));
        }
        return petitions;
    }

    private Petition getPetitionFromRow(ResultSet rs) throws SQLException {
        Petition petition = new Petition();
        petition.setUsername(rs.getString("username"));
        petition.setTime(LocalDateTime.parse(rs.getString("time")));
        petition.setPath(rs.getString("path"));
        petition.setMethod(rs.getString("method"));
        return petition;
    }
}
