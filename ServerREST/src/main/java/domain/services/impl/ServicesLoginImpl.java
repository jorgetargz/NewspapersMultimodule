package domain.services.impl;


import dao.LoginDao;
import domain.services.ServicesLogin;
import jakarta.inject.Inject;
import modelo.Reader;
import modelo.Secret;

import java.io.Serializable;

public class ServicesLoginImpl implements ServicesLogin, Serializable {

    private final LoginDao daoLogin;

    @Inject
    public ServicesLoginImpl(LoginDao daoLogin) {
        this.daoLogin = daoLogin;
    }

    @Override
    public Reader login(String username, char[] password) {
        return daoLogin.login(username, password);
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        return daoLogin.checkCredentials(username, password);
    }

    @Override
    public void saveVerifiedMail(String username, String email) {
       daoLogin.saveVerifiedMail(username, email);
    }

    @Override
    public boolean changePassword(String newPassword, String email) {
        return daoLogin.changePassword(newPassword, email);
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