package dao;

import modelo.Login;

public interface CredentialsDao {

    Login get(String username);

    Login get(int idReader);

    Login save(Login login);

    Login update(Login login);

    void delete(Login login);
}
