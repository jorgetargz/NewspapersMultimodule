package domain.services;

import modelo.Reader;
import modelo.Secret;


public interface ServicesLogin {

    Reader login(String username, char[] password);

    boolean checkCredentials(String username, String password);

    void saveVerifiedMail(String username, String email);

    boolean changePassword(String newPassword, String email);

    void updateSecretByUsername(Secret secret);

    void updateSecretByMail(Secret secret);

    Secret getSecret(String code);

    void sendVerificationEmail(Reader reader);

    void logout(String authorization);
}
