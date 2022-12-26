package gui.screens.readers.readers_add;

import domain.services.ReaderServices;
import gui.screens.common.ScreenConstants;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Login;
import modelo.Reader;

import java.time.LocalDate;

public class ReadersAddViewModel {

    private final ReaderServices servicesReaders;
    private final ObjectProperty<ReadersAddState> state;

    @Inject
    public ReadersAddViewModel(ReaderServices servicesReaders) {
        this.servicesReaders = servicesReaders;
        state = new SimpleObjectProperty<>(new ReadersAddState(null, null, null, null, false, false));
    }

    public ObjectProperty<ReadersAddState> getState() {
        return state;
    }


    public void loadReaders() {
        state.set(new ReadersAddState(null, null, null, null, true, false));
        servicesReaders.getReaders()
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isLeft()) {
                        state.set(new ReadersAddState(either.getLeft(), null, null, null, false, true));
                    } else {
                        state.set(new ReadersAddState(null, null, either.get(), null, false, true));
                    }
                });
    }

    public void addReader(String inputName, LocalDate inputBirthday, String inputUsername, String inputPassword, String inputEmail) {
        if (inputName != null && !inputName.isEmpty()
                && inputBirthday != null
                && inputUsername != null && !inputUsername.isEmpty()
                && inputPassword != null && !inputPassword.isEmpty()
                && inputEmail != null && !inputEmail.isEmpty()) {
            Reader reader = new Reader(inputName, inputBirthday, new Login(inputUsername, inputPassword, inputEmail));
            state.set(new ReadersAddState(null, null, null, null, true, false));
            servicesReaders.saveReader(reader)
                    .subscribeOn(Schedulers.single())
                    .subscribe(either -> {
                        if (either.isLeft())
                            state.set(new ReadersAddState(either.getLeft(), null, null, null, false, true));
                        else {
                            state.set(new ReadersAddState(null, ScreenConstants.OPERATION_DONE, null, either.get(), false, true));
                        }
                    });
        } else {
            state.set(new ReadersAddState(ScreenConstants.FILL_ALL_THE_INPUTS, null, null, null, false, true));
        }

    }

    public void cleanState() {
        state.set(new ReadersAddState(null, null, null, null, false, false));
    }
}
