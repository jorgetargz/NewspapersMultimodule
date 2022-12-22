package domain.services.impl;


import dao.LoginDao;
import dao.ReadersDao;
import dao.excepciones.DatabaseException;
import dao.excepciones.NotFoundException;
import domain.common.Constantes;
import domain.services.ServicesLogin;
import domain.services.excepciones.ValidationException;
import jakarta.beans.VerifyEmailBean;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.extern.log4j.Log4j2;
import modelo.Login;
import modelo.Reader;
import modelo.Secret;

import java.io.Serializable;

@Log4j2
public class ServicesLoginImpl implements ServicesLogin, Serializable {

    private final LoginDao daoLogin;
    private final ReadersDao daoReader;
    private final VerifyEmailBean verifyEmail;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public ServicesLoginImpl(LoginDao daoLogin, ReadersDao daoReader, VerifyEmailBean verifyEmail, Pbkdf2PasswordHash passwordHash) {
        this.daoLogin = daoLogin;
        this.daoReader = daoReader;
        this.verifyEmail = verifyEmail;
        this.passwordHash = passwordHash;
    }

    @Override
    public Reader login(String username, char[] password) {
        if (username == null || password == null) {
            throw new ValidationException(Constantes.USERNAME_OR_PASSWORD_EMPTY);
        }
        Login loginDB = daoLogin.getLogin(username);
        if (!passwordHash.verify(password, loginDB.getPassword())) {
            throw new ValidationException(Constantes.USERNAME_OR_PASSWORD_INCORRECT);
        } else if (loginDB.getEmail() == null){
            throw new ValidationException(Constantes.EMAIL_IS_NOT_VERIFIED);
        } else {
            return daoReader.getByUsername(username);
        }
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        if (username == null || password == null) {
            throw new ValidationException(Constantes.USERNAME_OR_PASSWORD_EMPTY);
        }
        Login loginDB = daoLogin.getLogin(username);
        return passwordHash.verify(password.toCharArray(), loginDB.getPassword());
    }

    @Override
    public void saveVerifiedMail(String username, String email) {
       daoLogin.updateLoginEmail(username, email);
    }

    @Override
    public boolean changePassword(String newPassword, String email) {
        newPassword = passwordHash.generate(newPassword.toCharArray());
        return daoLogin.updateLoginPassword(newPassword, email);
    }

    @Override
    public void updateSecretByUsername(Secret secret) {
        daoLogin.updateSecretByUsername(secret);
    }

    @Override
    public void updateSecretByMail(Secret secret) {
        daoLogin.updateSecretByMail(secret);
    }

    @Override
    public Secret getSecret(String code) {
        return daoLogin.getSecret(code);
    }

    @Override
    public void sendVerificationEmail(Reader reader) {
        verifyEmail.setEmail(reader.getLogin().getEmail());
        verifyEmail.setUsername(reader.getLogin().getUsername());
        try {
            verifyEmail.sendVerificationEmail();
        } catch (MessagingException e) {
            daoReader.delete(reader);
            log.error(e.getMessage(), e);
            throw new ValidationException(e.getMessage());
        } catch (NotFoundException | DatabaseException e) {
            daoReader.delete(reader);
            throw e;
        }

    }

}