package gui.screens.newspapers.newspapers_list;

import domain.services.NewspaperServices;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class NewspapersListViewModel {

    private final NewspaperServices servicesNewspapers;
    private final ObjectProperty<NewspapersListState> state;

    @Inject
    public NewspapersListViewModel(NewspaperServices servicesNewspapers) {
        this.servicesNewspapers = servicesNewspapers;
        state = new SimpleObjectProperty<>(new NewspapersListState(null, null, false, false));
    }

    public ReadOnlyObjectProperty<NewspapersListState> getState() {
        return state;
    }


    public void loadNewspapers() {
        state.set(new NewspapersListState(null, null, true, false));
        servicesNewspapers.getNewspapers()
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isLeft()) {
                        state.set(new NewspapersListState(either.getLeft(), null, false, true));
                    } else {
                        state.set(new NewspapersListState(null, either.get(), false, true));
                    }
                });
    }

    public void cleanState() {
        state.set(new NewspapersListState(null, null, false, false));
    }
}
