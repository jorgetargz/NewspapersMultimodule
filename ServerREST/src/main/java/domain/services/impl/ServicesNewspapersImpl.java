package domain.services.impl;

import dao.NewspapersDao;
import domain.services.ServicesNewspapers;
import jakarta.inject.Inject;
import modelo.Newspaper;

import java.util.List;

public class ServicesNewspapersImpl implements ServicesNewspapers {

    private final NewspapersDao daoNewspapers;

    @Inject
    public ServicesNewspapersImpl(NewspapersDao daoNewspapers) {
        this.daoNewspapers = daoNewspapers;
    }

    @Override
    public List<Newspaper> getNewspapers() {
        return daoNewspapers.getAll();
    }

    @Override
    public Newspaper get(int id) {
        return daoNewspapers.get(id);
    }

    @Override
    public Newspaper saveNewspaper(Newspaper newspaper) {
        return daoNewspapers.save(newspaper);
    }

    @Override
    public Newspaper updateNewspaper(Newspaper newspaper) {
        return daoNewspapers.update(newspaper);
    }

    @Override
    public void deleteNewspaper(Newspaper newspaper) {
        daoNewspapers.delete(newspaper);
    }

}
