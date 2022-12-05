package gui.screens.readers.readers_delete;

import domain.services.ReaderServices;
import gui.screens.common.ScreenConstants;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Reader;

public class ReadersDeleteViewModel {

    private final ReaderServices servicesReaders;
    private final ObjectProperty<ReadersDeleteState> state;

    @Inject
    public ReadersDeleteViewModel(ReaderServices servicesReaders) {
        this.servicesReaders = servicesReaders;
        state = new SimpleObjectProperty<>(new ReadersDeleteState(null, null, null, null));
    }

    public ObjectProperty<ReadersDeleteState> getState() {
        return state;
    }


    public void loadReaders() {
        servicesReaders.getReaders()
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isLeft())
                        state.set(new ReadersDeleteState(either.getLeft(), null, null, null));
                    else {
                        state.set(new ReadersDeleteState(null, null, either.get(), null));
                    }
                });
    }

    public void deleteReader(Reader reader) {
        if (reader != null) {
            servicesReaders.deleteReader(reader)
                    .subscribeOn(Schedulers.single())
                    .subscribe(either -> {
                        if (either.isLeft()) {
                            state.set(new ReadersDeleteState(either.getLeft(), null, null, null));
                        } else if (Boolean.TRUE.equals(either.get())) {
                            state.set(new ReadersDeleteState(null, ScreenConstants.OPERATION_DONE, null, reader));
                        }
                    });
        } else {
            state.set(new ReadersDeleteState(ScreenConstants.CHOOSE_READER, null, null, null));
        }
    }

    public void cleanState() {
        state.set(new ReadersDeleteState(null, null, null, null));
    }
}
