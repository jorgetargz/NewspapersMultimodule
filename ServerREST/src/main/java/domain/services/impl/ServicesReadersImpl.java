package domain.services.impl;


import common.ConstantesAPI;
import dao.ReadersDao;
import domain.common.Constantes;
import domain.services.ServicesReaders;
import domain.services.excepciones.ValidationException;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import modelo.Reader;

import java.util.List;

public class ServicesReadersImpl implements ServicesReaders {

    private final ReadersDao daoReaders;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public ServicesReadersImpl(ReadersDao daoReaders, Pbkdf2PasswordHash passwordHash) {
        this.daoReaders = daoReaders;
        this.passwordHash = passwordHash;
    }

    @Override
    public List<Reader> getReaders() {
        return daoReaders.getAll();
    }

    @Override
    public Reader saveReader(Reader reader) {
        reader.getLogin().setRole(ConstantesAPI.ROLE_READER);
        return daoReaders.save(hashPassword(reader));
    }

    @Override
    public Reader updateReader(Reader reader) {
        return daoReaders.update(hashPassword(reader));
    }

    @Override
    public void deleteReader(Reader reader) {
        daoReaders.delete(reader);
    }

    @Override
    public List<Reader> getReadersByNewspaper(String newspaper) {
        try {
            return daoReaders.getAllByNewspaperId(Integer.parseInt(newspaper));
        } catch (NumberFormatException e) {
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public List<Reader> getReadersByArticleType(String articleType) {
        try {
            return daoReaders.getAllByArticleTypeId(Integer.parseInt(articleType));
        } catch (NumberFormatException e) {
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public Reader getReader(String id) {
        try {
            return daoReaders.get(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            throw new ValidationException(Constantes.PARAMETER_ID_MUST_BE_A_NUMBER);
        }
    }

    @Override
    public Reader getReaderByUsername(String name) {
        return daoReaders.getByUsername(name);
    }

    private Reader hashPassword(Reader reader) {
        if (reader.getLogin().getPassword() != null) {
            reader.changePassword(passwordHash.generate(reader.getLogin().getPassword().toCharArray()));
        }
        return reader;
    }
}