package dao.jdbc_impl;

import dao.DBConnection;
import dao.SecretsDao;
import dao.common.Constantes;
import dao.excepciones.DatabaseException;
import dao.excepciones.NotFoundException;
import dao.utils.SQLQueries;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.Secret;

import java.sql.*;
import java.time.LocalDateTime;

@Log4j2
public class SecretsDaoImpl implements SecretsDao {

    private final DBConnection dbConnection;

    @Inject
    public SecretsDaoImpl(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Secret getSecret(String code) {
        try (Connection con = dbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_SECRET_CODE_BY_CODE_QUERY)) {
            preparedStatement.setString(1, code);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return getSecretFromRow(rs);
            } else {
                log.warn(Constantes.SECRET_NOT_FOUND);
                throw new NotFoundException(Constantes.SECRET_NOT_FOUND);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
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
                log.warn(Constantes.SECRET_NOT_FOUND);
                throw new NotFoundException(Constantes.SECRET_NOT_FOUND);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            if (ex instanceof SQLIntegrityConstraintViolationException) {
                throw new DatabaseException(Constantes.EMAIL_ALREADY_EXISTS);
            } else {
                throw new DatabaseException(Constantes.DATABASE_ERROR);
            }
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
                log.warn(Constantes.SECRET_NOT_FOUND);
                throw new NotFoundException(Constantes.SECRET_NOT_FOUND);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new DatabaseException(Constantes.DATABASE_ERROR);
        }
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