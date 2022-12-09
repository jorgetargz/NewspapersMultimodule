package dao.impl;

import dao.CredentialsDao;
import dao.DBConnection;
import dao.common.Constantes;
import dao.excepciones.DatabaseException;
import dao.utils.SQLQueries;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j2
public class CredentialsDaoImpl implements CredentialsDao {

    private final DBConnection dbConnection;

    @Inject
    public CredentialsDaoImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Login get(String username) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_LOGIN_BY_USERNAME)) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            return getLoginFromRS(rs);
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public Login get(int idReader) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_LOGIN_FROM_ID_QUERY)) {
            preparedStatement.setInt(1, idReader);
            ResultSet rs = preparedStatement.executeQuery();
            return getLoginFromRS(rs);
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public Login save(Login login) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_LOGIN_QUERY)) {
            preparedStatement.setString(1, login.getUsername());
            preparedStatement.setString(2, login.getPassword());
            preparedStatement.setInt(3, login.getIdReader());
            preparedStatement.executeUpdate();
            return login;
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public Login update(Login login) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_LOGIN_QUERY)) {
            preparedStatement.setString(1, login.getPassword());
            preparedStatement.setString(2, login.getUsername());
            preparedStatement.executeUpdate();
            return login;
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public void delete(Login login) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.DELETE_LOGIN_QUERY)) {
            preparedStatement.setString(1, login.getUsername());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    private Login getLoginFromRS(ResultSet rs) throws SQLException {
        if (rs.next()) {
            int idReader = rs.getInt(Constantes.ID_READER);
            String username = rs.getString(Constantes.USERNAME);
            String pass = rs.getString(Constantes.PASSWORD);
            String email = rs.getString(Constantes.MAIL);
            return new Login(username, pass, email, idReader);
        }
        return null;
    }
}
