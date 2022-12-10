package dao.impl;

import dao.DBConnection;
import dao.LoginDao;
import dao.common.Constantes;
import dao.excepciones.DatabaseException;
import dao.excepciones.NotFoundException;
import dao.excepciones.UnauthorizedException;
import dao.utils.SQLQueries;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.extern.log4j.Log4j2;
import modelo.Login;
import modelo.Reader;
import modelo.Secret;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Log4j2
public class LoginDaoImpl implements LoginDao {


    private final DBConnection dbConnection;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public LoginDaoImpl(DBConnection dbConnection, Pbkdf2PasswordHash passwordHash) {
        this.dbConnection = dbConnection;
        this.passwordHash = passwordHash;
    }

    @Override
    public Reader login(String username, String password) {
        try (Connection con = dbConnection.getConnection()) {
            con.setAutoCommit(false);
            try (
                    PreparedStatement preparedStatementGetReader = con.prepareStatement(SQLQueries.SELECT_READER_BY_USERNAME_QUERY);
                    PreparedStatement preparedStatementCheckMail = con.prepareStatement(SQLQueries.SELECT_LOGIN_BY_USERNAME_QUERY)
            ) {
                preparedStatementGetReader.setString(1, username);
                ResultSet rsReader = preparedStatementGetReader.executeQuery();
                if (rsReader.next()) {
                    preparedStatementCheckMail.setString(1, username);
                    ResultSet rsLogin = preparedStatementCheckMail.executeQuery();
                    if (rsLogin.next() && rsLogin.getString(Constantes.MAIL) != null) {
                        if (passwordHash.verify(password.toCharArray(), rsLogin.getString(Constantes.PASSWORD))) {
                            con.commit();
                            return getReaderFromResultSets(rsReader, rsLogin);
                        } else {
                            throw new UnauthorizedException(Constantes.WRONG_PASSWORD);
                        }
                    } else {
                        con.rollback();
                        throw new UnauthorizedException(Constantes.VERIFY_YOUR_EMAIL_FIRST);
                    }
                } else {
                    log.info(Constantes.READER_NOT_FOUND);
                    throw new NotFoundException(Constantes.READER_NOT_FOUND);
                }
            } catch (SQLException e) {
                con.rollback();
                log.error(e.getMessage(), e);
                throw new DatabaseException(e.getMessage());
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        try (Connection con = dbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_LOGIN_BY_USERNAME)) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String passwordDB = rs.getString(Constantes.PASSWORD);
                return passwordHash.verify(password.toCharArray(), passwordDB);
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
    public boolean saveVerifiedMail(String username, String email) {
        try (Connection con = dbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_READER_MAIL_QUERY)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, username);
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
    public boolean changePassword(String newPassword, String email) {
        try (Connection con = dbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_LOGIN_PASSWORD_BY_EMAIL_QUERY)) {
            preparedStatement.setString(1, passwordHash.generate(newPassword.toCharArray()));
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
    public void updateSecret(Secret secret) {
        try (Connection con = dbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_SECRET_CODE_QUERY)) {
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

    private Secret getSecretFromRow(ResultSet rs) throws SQLException {
        Secret secret = new Secret();
        secret.setCode(rs.getString(Constantes.CODE));
        secret.setCodeExpirationDate(LocalDateTime.parse(rs.getString(Constantes.CODE_EXPIRATION_DATE)));
        secret.setUsername(rs.getString(Constantes.USERNAME));
        secret.setEmail(rs.getString(Constantes.EMAIL));
        return secret;
    }

    private Reader getReaderFromResultSets(ResultSet readerRS, ResultSet loginRS) throws SQLException {
        Reader reader = new Reader();
        reader.setId(readerRS.getInt(Constantes.ID));
        reader.setName(readerRS.getString(Constantes.NAME_READER));
        reader.setDateOfBirth(readerRS.getDate(Constantes.BIRTH_READER).toLocalDate());
        Login login = new Login();
        login.setUsername(loginRS.getString(Constantes.USERNAME));
        login.setPassword(loginRS.getString(Constantes.PASSWORD));
        login.setIdReader(reader.getId());
        login.setEmail(loginRS.getString(Constantes.EMAIL));
        reader.setLogin(login);
        return reader;
    }


}