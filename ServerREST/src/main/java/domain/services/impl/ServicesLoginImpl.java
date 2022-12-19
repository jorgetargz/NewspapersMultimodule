package domain.services.impl;


import dao.LoginDao;
import dao.ReadersDao;
import domain.services.ServicesLogin;
import domain.services.excepciones.ValidationException;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import modelo.Login;
import modelo.Reader;
import modelo.Secret;

import java.io.Serializable;

public class ServicesLoginImpl implements ServicesLogin, Serializable {

    private final LoginDao daoLogin;
    private final ReadersDao daoReader;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public ServicesLoginImpl(LoginDao daoLogin, ReadersDao daoReader, Pbkdf2PasswordHash passwordHash) {
        this.daoLogin = daoLogin;
        this.daoReader = daoReader;
        this.passwordHash = passwordHash;
    }

    @Override
    public Reader login(String username, char[] password) {
        if (username == null || password == null) {
            throw new ValidationException("Username or password empty");
        }
        Login loginDB = daoLogin.getLogin(username);
        if (!passwordHash.verify(password, loginDB.getPassword())) {
            throw new ValidationException("Username or password incorrect");
        } else if (loginDB.getEmail() == null){
            throw new ValidationException("Email is not verified");
        } else {
            return daoReader.getByUsername(username);
        }
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        if (username == null || password == null) {
            throw new ValidationException("Username or password empty");
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

}