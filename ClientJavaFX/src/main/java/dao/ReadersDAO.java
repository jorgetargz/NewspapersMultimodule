package dao;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import modelo.Reader;

import java.util.List;


public interface ReadersDAO {

    Single<Either<String, List<Reader>>> getReaders();

    Single<Either<String, Reader>> saveReader(Reader reader);

    Single<Either<String, Reader>> updateReader(Reader reader);

    Single<Either<String, Boolean>> deleteReader(Reader reader);
}
