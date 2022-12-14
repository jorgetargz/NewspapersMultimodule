package dao;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import modelo.Reader;


public interface LoginDAO {

    Single<Either<String, Reader>> getReaderByLogin(String basicAuthCredential);

    Single<Either<String, Reader>> logout();

    Single<Either<String, Reader>> registerReader(Reader reader);
}
