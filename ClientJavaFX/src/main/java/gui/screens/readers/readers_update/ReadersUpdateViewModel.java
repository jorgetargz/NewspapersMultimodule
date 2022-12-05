package gui.screens.readers.readers_update;

import domain.services.ReaderServices;
import gui.screens.common.ScreenConstants;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Login;
import modelo.Reader;

import java.time.LocalDate;

public class ReadersUpdateViewModel {

    private final ReaderServices servicesReaders;
    private final ObjectProperty<ReadersUpdateState> state;

    @Inject
    public ReadersUpdateViewModel(ReaderServices servicesReaders) {
        this.servicesReaders = servicesReaders;
        state = new SimpleObjectProperty<>(new ReadersUpdateState(null, null, null, null));
    }

    public ObjectProperty<ReadersUpdateState> getState() {
        return state;
    }


    public void loadReaders() {
        servicesReaders.getReaders()
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isLeft())
                        state.set(new ReadersUpdateState(either.getLeft(), null, null, null));
                    else {
                        state.set(new ReadersUpdateState(null, null, either.get(), null));
                    }
                });
    }

    public void updateRedaer(Reader dbReader, String inputName, LocalDate inputBirthday, String inputPassword) {
        if (dbReader != null) {
            Reader reader = new Reader(dbReader.getId(), inputName, inputBirthday,
                    new Login(dbReader.getLogin().getUsername(), inputPassword, dbReader.getId()));
            servicesReaders.updateReader(reader)
                    .subscribeOn(Schedulers.single())
                    .subscribe(either -> {
                        if (either.isLeft())
                            state.set(new ReadersUpdateState(either.getLeft(), null, null, null));
                        else {
                            state.set(new ReadersUpdateState(null, ScreenConstants.OPERATION_DONE, null, either.get()));
                        }
                    });
        } else {
            state.set(new ReadersUpdateState(ScreenConstants.CHOOSE_READER, null, null, null));
        }
    }

    public void cleanState() {
        state.set(new ReadersUpdateState(null, null, null, null));
    }

}
