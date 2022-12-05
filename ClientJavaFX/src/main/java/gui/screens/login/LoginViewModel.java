package gui.screens.login;

import domain.services.LoginServices;
import gui.screens.common.ScreenConstants;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Login;
import modelo.Reader;

import java.time.LocalDate;

public class LoginViewModel {

    private final LoginServices loginServices;
    private final ObjectProperty<LoginState> state;

    @Inject
    public LoginViewModel(LoginServices loginServices) {
        this.loginServices = loginServices;
        state = new SimpleObjectProperty<>(new LoginState(null, null, false));
    }

    public ReadOnlyObjectProperty<LoginState> getState() {
        return state;
    }

    public void doLogin(String username, String password) {
        loginServices.getReaderByLogin(username, password)
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isLeft())
                        state.set(new LoginState(null, either.getLeft(), false));
                    else {
                        state.set(new LoginState(either.get(), null, false));
                    }
                });
    }

    public void doRegister(String inputName, LocalDate inputBirthday, String inputUsername, String inputPassword) {
        if (inputName != null && !inputName.isEmpty()
                && inputBirthday != null
                && inputUsername != null && !inputUsername.isEmpty()
                && inputPassword != null && !inputPassword.isEmpty()) {
            Reader reader = new Reader(inputName, inputBirthday, new Login(inputUsername, inputPassword));
            loginServices.registerReader(reader)
                    .subscribeOn(Schedulers.single())
                    .subscribe(either -> {
                        if (either.isLeft())
                            state.set(new LoginState(null, either.getLeft(), false));
                        else {
                            state.set(new LoginState(null, null, true));
                        }
                    });
        } else {
            state.set(new LoginState(null, ScreenConstants.FILL_ALL_THE_INPUTS, false));
        }
    }

    public void clenState() {
        state.setValue(new LoginState(null, null, false));
    }
}
