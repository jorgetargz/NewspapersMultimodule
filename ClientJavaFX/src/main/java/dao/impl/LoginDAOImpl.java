package dao.impl;

import com.google.gson.Gson;
import dao.LoginDAO;
import dao.newspapers_api.LoginAPI;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Reader;

public class LoginDAOImpl extends GenericDAO implements LoginDAO {

    private final LoginAPI loginAPI;

    @Inject
    public LoginDAOImpl(Gson gson, LoginAPI loginAPI) {
        super(gson);
        this.loginAPI = loginAPI;
    }

    @Override
    public Single<Either<String, Reader>> getReaderByLogin(String authorization) {
        return safeAPICall(loginAPI.getReaderByLogin(authorization));
    }

    @Override
    public Single<Either<String, Boolean>> logout(String authorization) {
        return safeAPICallResponseVoid(loginAPI.logout(authorization));
    }

    @Override
    public Single<Either<String, Reader>> registerReader(Reader reader) {
        return safeAPICall(loginAPI.registerReader(reader));
    }
}
