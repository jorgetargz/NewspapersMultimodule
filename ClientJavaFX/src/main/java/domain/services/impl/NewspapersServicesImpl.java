package domain.services.impl;

import dao.NewspapersDAO;
import domain.services.NewspaperServices;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Newspaper;

import java.util.List;

public class NewspapersServicesImpl implements NewspaperServices {

    private final NewspapersDAO newspapersDAO;

    @Inject
    public NewspapersServicesImpl(NewspapersDAO newspapersDAO) {
        this.newspapersDAO = newspapersDAO;
    }

    @Override
    public Single<Either<String, List<Newspaper>>> getNewspapers() {
        return newspapersDAO.getNewspapers();
    }

    @Override
    public Single<Either<String, Newspaper>> saveNewspaper(Newspaper newspaper) {
        return newspapersDAO.saveNewspaper(newspaper);
    }

    @Override
    public Single<Either<String, Newspaper>> updateNewspaper(Newspaper newspaper) {
        return newspapersDAO.updateNewspaper(newspaper);
    }

    @Override
    public Single<Either<String, Boolean>> deleteNewspaper(Newspaper newspaper) {
        return newspapersDAO.deleteNewspaper(newspaper);
    }
}
