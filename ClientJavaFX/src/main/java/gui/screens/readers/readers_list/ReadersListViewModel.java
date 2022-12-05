package gui.screens.readers.readers_list;

import domain.services.ReaderServices;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ReadersListViewModel {

    private final ReaderServices servicesReaders;
    private final ObjectProperty<ReadersListState> state;

    @Inject
    public ReadersListViewModel(ReaderServices servicesReaders) {
        this.servicesReaders = servicesReaders;
        state = new SimpleObjectProperty<>(new ReadersListState(null, null));
    }

    public ObjectProperty<ReadersListState> getState() {
        return state;
    }


    public void loadReaders() {
        servicesReaders.getReaders()
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isLeft())
                        state.set(new ReadersListState(either.getLeft(), null));
                    else {
                        state.set(new ReadersListState(null, either.get()));
                    }
                });
    }

    public void cleanState() {
        state.set(new ReadersListState(null, null));
    }

}
