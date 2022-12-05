package domain.services.impl;

import dao.ReadersDAO;
import domain.services.ReaderServices;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Reader;

import java.util.List;

public class ReaderServicesImpl implements ReaderServices {

    private final ReadersDAO readersDAO;

    @Inject
    public ReaderServicesImpl(ReadersDAO readersDAO) {
        this.readersDAO = readersDAO;
    }

    @Override
    public Single<Either<String, List<Reader>>> getReaders() {
        return readersDAO.getReaders();
    }

    @Override
    public Single<Either<String, Reader>> saveReader(Reader reader) {
        return readersDAO.saveReader(reader);
    }

    @Override
    public Single<Either<String, Reader>> updateReader(Reader reader) {
        return readersDAO.updateReader(reader);
    }

    @Override
    public Single<Either<String, Boolean>> deleteReader(Reader reader) {
        return readersDAO.deleteReader(reader);
    }

}
