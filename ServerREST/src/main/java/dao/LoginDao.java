package dao;

import modelo.Reader;
import modelo.Secret;

public interface LoginDao {

    Reader login(String username, char[] password);

    boolean checkCredentials(String username, String password);

    void saveVerifiedMail(String username, String email);

    boolean changePassword(String newPassword, String email);

    void updateSecretByUsername(Secret secret);

    void updateSecretByMail(Secret secret);

    Secret getSecret(String code);
}
