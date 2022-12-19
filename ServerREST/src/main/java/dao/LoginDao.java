package dao;

import modelo.Login;
import modelo.Secret;

public interface LoginDao {

    Login getLogin(String username);

    Secret getSecret(String code);

    void updateLoginEmail(String username, String email);

    boolean updateLoginPassword(String newPassword, String email);

    void updateSecretByUsername(Secret secret);

    void updateSecretByMail(Secret secret);
}
