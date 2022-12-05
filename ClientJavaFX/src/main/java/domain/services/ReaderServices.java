package domain.services;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import modelo.Reader;

import java.util.List;

public interface ReaderServices {

    Single<Either<String, List<Reader>>> getReaders();

    Single<Either<String, Reader>> saveReader(Reader reader);

    Single<Either<String, Reader>> updateReader(Reader reader);

    Single<Either<String, Boolean>> deleteReader(Reader reader);
}
