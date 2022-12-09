package domain.services;

import modelo.Reader;


public interface ServicesLogin {

    Reader login(String username, String password);

    boolean checkCredentials(String username, String password);

    boolean saveVerifiedMail(String username, String email);

    boolean changePassword(String newPassword, String email);
}
