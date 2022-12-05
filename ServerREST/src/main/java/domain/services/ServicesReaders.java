package domain.services;

import modelo.Reader;

import java.util.List;

public interface ServicesReaders {
    List<Reader> getReaders();

    List<Reader> getReadersByNewspaper(String newspaperId);

    List<Reader> getReadersByArticleType(String articleTypeId);

    Reader getReader(String id);

    Reader saveReader(Reader reader);

    Reader updateReader(Reader reader);

    void deleteReader(Reader reader);

}
