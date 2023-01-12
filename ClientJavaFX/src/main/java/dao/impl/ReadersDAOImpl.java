package dao.impl;

import com.google.gson.Gson;
import dao.ReadersDAO;
import dao.newspapers_api.ReadersAPI;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.Reader;

import java.util.List;

@Log4j2
public class ReadersDAOImpl extends GenericDAO implements ReadersDAO {

    private final ReadersAPI readersAPI;

    @Inject
    public ReadersDAOImpl(ReadersAPI readersAPI, Gson gson) {
        super(gson);
        this.readersAPI = readersAPI;
    }

    @Override
    public Single<Either<String, List<Reader>>> getReaders() {
        return safeAPICall(readersAPI.getReaders());
    }

    @Override
    public Single<Either<String, Reader>> saveReader(Reader reader) {
        return safeAPICall(readersAPI.saveReader(reader));
    }

    @Override
    public Single<Either<String, Reader>> updateReader(Reader reader) {
        return safeAPICall(readersAPI.updateReader(reader));
    }

    @Override
    public Single<Either<String, Boolean>> deleteReader(Reader reader) {
        return safeAPICallResponseVoid(readersAPI.deleteReader(reader.getId()));
    }

}
