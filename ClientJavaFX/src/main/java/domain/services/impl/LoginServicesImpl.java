package domain.services.impl;

import dao.LoginDAO;
import dao.newspapers_api.utils.CacheAuthorization;
import domain.services.LoginServices;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Reader;
import okhttp3.Credentials;

public class LoginServicesImpl implements LoginServices {

    private final LoginDAO loginDAO;
    private final CacheAuthorization cache;

    @Inject
    public LoginServicesImpl(LoginDAO loginDAO, CacheAuthorization cache) {
        this.loginDAO = loginDAO;
        this.cache = cache;
    }

    @Override
    public Single<Either<String, Reader>> getReaderByLogin(String username, String password) {
        cache.setUser(username);
        cache.setPassword(password);
        return loginDAO.getReaderByLogin(Credentials.basic(username, password));
    }

    @Override
    public Single<Either<String, Boolean>> logout() {
        String jwtAuth = cache.getJwtAuth();
        cache.setUser(null);
        cache.setPassword(null);
        cache.setJwtAuth(null);
        return loginDAO.logout(jwtAuth);
    }

    @Override
    public Single<Either<String, Reader>> registerReader(Reader reader) {
        return loginDAO.registerReader(reader);
    }
}
