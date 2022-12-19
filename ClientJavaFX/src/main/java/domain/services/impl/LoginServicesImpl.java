package domain.services.impl;

import dao.LoginDAO;
import domain.services.LoginServices;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Reader;

import java.util.Base64;

public class LoginServicesImpl implements LoginServices {

    private final LoginDAO loginDAO;

    @Inject
    public LoginServicesImpl(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

    @Override
    public Single<Either<String, Reader>> getReaderByLogin(String username, String password) {
        String credentials = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        String basicAuthCredential = "BASIC " + credentials;
        return loginDAO.getReaderByLogin(basicAuthCredential);
    }

    @Override
    public Single<Either<String, Boolean>> logout() {
        return loginDAO.logout();
    }

    @Override
    public Single<Either<String, Reader>> registerReader(Reader reader) {
        return loginDAO.registerReader(reader);
    }
}
