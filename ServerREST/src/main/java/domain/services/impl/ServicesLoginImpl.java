package domain.services.impl;


import dao.LoginDao;
import domain.services.ServicesLogin;
import jakarta.inject.Inject;
import modelo.Reader;

import java.io.Serializable;

public class ServicesLoginImpl implements ServicesLogin, Serializable {

    private final LoginDao daoLogin;

    @Inject
    public ServicesLoginImpl(LoginDao daoLogin) {
        this.daoLogin = daoLogin;
    }

    @Override
    public Reader login(String username, String password) {
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
    public boolean saveVerifiedMail(String username, String email) {
        return daoLogin.saveVerifiedMail(username, email);
    }

    @Override
    public boolean changePassword(String newPassword, String email) {
        return daoLogin.changePassword(newPassword, email);
    }

}