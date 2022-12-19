package dao.jdbc_impl;

import dao.DBConnection;
import dao.LoginDao;
import dao.common.Constantes;
import dao.excepciones.DatabaseException;
import dao.excepciones.NotFoundException;
import dao.utils.SQLQueries;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.Login;
import modelo.Secret;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Log4j2
public class LoginDaoImpl implements LoginDao {

    private final DBConnection dbConnection;

    @Inject
    public LoginDaoImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Login getLogin(String username) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_LOGIN_BY_USERNAME_QUERY)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getLoginFromResultSet(resultSet);
            } else {
                throw new NotFoundException(Constantes.LOGIN_NOT_FOUND);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Secret getSecret(String code) {
        try (Connection con = dbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_SECRET_CODE_BY_CODE_QUERY)) {
            preparedStatement.setString(1, code);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return getSecretFromRow(rs);
            } else {
                log.info(Constantes.SECRET_NOT_FOUND);
                throw new NotFoundException(Constantes.SECRET_NOT_FOUND);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public void updateLoginEmail(String username, String email) {
        try (Connection con = dbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_READER_MAIL_QUERY)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, username);
            int rows = preparedStatement.executeUpdate();
            if (rows != 1) {
                log.info(Constantes.READER_NOT_FOUND);
                throw new NotFoundException(Constantes.READER_NOT_FOUND);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public boolean updateLoginPassword(String newPassword, String email) {
        try (Connection con = dbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_LOGIN_PASSWORD_BY_EMAIL_QUERY)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, email);
            int rows = preparedStatement.executeUpdate();
            if (rows == 1) {
                return true;
            } else {
                log.info(Constantes.READER_NOT_FOUND);
                throw new NotFoundException(Constantes.READER_NOT_FOUND);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public void updateSecretByUsername(Secret secret) {
        try (Connection con = dbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_SECRET_CODE_BY_USERNAME_QUERY)) {
            preparedStatement.setString(1, secret.getCode());
            preparedStatement.setString(2, secret.getCodeExpirationDate().toString());
            preparedStatement.setString(3, secret.getEmail());
            preparedStatement.setString(4, secret.getUsername());
            int rows = preparedStatement.executeUpdate();
            if (rows != 1) {
                log.info(Constantes.SECRET_NOT_FOUND);
                throw new NotFoundException(Constantes.SECRET_NOT_FOUND);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public void updateSecretByMail(Secret secret) {
        try (Connection con = dbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_SECRET_CODE_BY_MAIL_QUERY)) {
            preparedStatement.setString(1, secret.getCode());
            preparedStatement.setString(2, secret.getCodeExpirationDate().toString());
            preparedStatement.setString(3, secret.getEmail());
            int rows = preparedStatement.executeUpdate();
            if (rows != 1) {
                log.info(Constantes.SECRET_NOT_FOUND);
                throw new NotFoundException(Constantes.SECRET_NOT_FOUND);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    private Login getLoginFromResultSet(ResultSet resultSet) throws SQLException {
        Login login = new Login();
        login.setUsername(resultSet.getString(Constantes.USERNAME));
        login.setPassword(resultSet.getString(Constantes.PASSWORD));
        login.setIdReader(resultSet.getInt(Constantes.ID_READER));
        login.setRole(resultSet.getString(Constantes.ROLE));
        login.setEmail(resultSet.getString(Constantes.EMAIL));
        return login;
    }

    private Secret getSecretFromRow(ResultSet rs) throws SQLException {
        Secret secret = new Secret();
        secret.setCode(rs.getString(Constantes.CODE));
        secret.setCodeExpirationDate(LocalDateTime.parse(rs.getString(Constantes.CODE_EXPIRATION_DATE)));
        secret.setUsername(rs.getString(Constantes.USERNAME));
        secret.setEmail(rs.getString(Constantes.EMAIL));
        return secret;
    }
}