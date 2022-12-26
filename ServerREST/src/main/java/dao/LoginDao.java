package dao;

import modelo.Login;

public interface LoginDao {

    Login getLogin(String username);

    void updateLoginEmail(String username, String email);

    boolean updateLoginPassword(String newPassword, String email);

}
