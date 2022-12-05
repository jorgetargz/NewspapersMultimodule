package dao;

import modelo.Newspaper;

import java.util.List;

public interface NewspapersDao {
    List<Newspaper> getAll();

    Newspaper get(int id);

    Newspaper save(Newspaper newspaper);

    Newspaper update(Newspaper newspaper);

    void delete(Newspaper newspaper);
}
