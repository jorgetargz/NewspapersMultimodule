package dao.impl;

import com.google.gson.Gson;
import dao.ReadersDAO;
import dao.newspapers_api.NewspapersAPI;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.Reader;

import java.util.List;

@Log4j2
public class ReadersDAOImpl extends GenericDAO implements ReadersDAO {

    private final NewspapersAPI newspapersAPI;

    @Inject
    public ReadersDAOImpl(NewspapersAPI newspapersAPI, Gson gson) {
        super(gson);
        this.newspapersAPI = newspapersAPI;
    }

    @Override
    public Single<Either<String, List<Reader>>> getReaders() {
        return safeAPICall(newspapersAPI.getReaders());
    }

    @Override
    public Single<Either<String, Reader>> saveReader(Reader reader) {
        return safeAPICall(newspapersAPI.saveReader(reader));
    }

    @Override
    public Single<Either<String, Reader>> updateReader(Reader reader) {
        return safeAPICall(newspapersAPI.updateReader(reader));
    }

    @Override
    public Single<Either<String, Boolean>> deleteReader(Reader reader) {
        return safeAPICallToDelete(newspapersAPI.deleteReader(reader.getId()));
    }

}
