package domain.services;

import modelo.Newspaper;

import java.util.List;

public interface ServicesNewspapers {

    List<Newspaper> getNewspapers();

    Newspaper get(int id);

    Newspaper saveNewspaper(Newspaper newspaper);

    Newspaper updateNewspaper(Newspaper newspaper);

    void deleteNewspaper(Newspaper newspaper);

}
