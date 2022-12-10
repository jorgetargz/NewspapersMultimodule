package domain.services;

import modelo.Reader;
import modelo.Secret;


public interface ServicesLogin {

    Reader login(String username, String password);

    boolean checkCredentials(String username, String password);

    boolean saveVerifiedMail(String username, String email);

    boolean changePassword(String newPassword, String email);

    void updateSecretByUsername(Secret secret);

    void updateSecretByMail(Secret secret);

    Secret getSecret(String code);
}
