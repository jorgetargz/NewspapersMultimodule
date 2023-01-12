package dao.impl;

import com.google.gson.Gson;
import dao.NewspapersDAO;
import dao.newspapers_api.NewspapersAPI;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Newspaper;

import java.util.List;

public class NewspapersDAOImpl extends GenericDAO implements NewspapersDAO {

    private final NewspapersAPI newspapersAPI;

    @Inject
    public NewspapersDAOImpl(NewspapersAPI newspapersAPI, Gson gson) {
        super(gson);
        this.newspapersAPI = newspapersAPI;
    }

    @Override
    public Single<Either<String, List<Newspaper>>> getNewspapers() {
        return safeAPICall(newspapersAPI.getNewspapers());
    }

    @Override
    public Single<Either<String, Newspaper>> saveNewspaper(Newspaper newspaper) {
        return safeAPICall(newspapersAPI.saveNewspaper(newspaper));
    }

    @Override
    public Single<Either<String, Newspaper>> updateNewspaper(Newspaper newspaper) {
        return safeAPICall(newspapersAPI.updateNewspaper(newspaper));
    }

    @Override
    public Single<Either<String, Boolean>> deleteNewspaper(Newspaper newspaper) {
        return safeAPICallResponseVoid(newspapersAPI.deleteNewspaper(newspaper.getId()));
    }
}
