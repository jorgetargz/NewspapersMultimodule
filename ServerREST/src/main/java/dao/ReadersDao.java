package dao;

import modelo.Reader;

import java.util.List;

public interface ReadersDao {
    List<Reader> getAll();

    List<Reader> getAllByNewspaperId(int newspaperId);

    List<Reader> getAllByArticleTypeId(int articleTypeId);

    Reader get(int id);

    Reader getByUsername(String name);

    Reader save(Reader reader);

    Reader update(Reader reader);

    void delete(Reader reader);
}
