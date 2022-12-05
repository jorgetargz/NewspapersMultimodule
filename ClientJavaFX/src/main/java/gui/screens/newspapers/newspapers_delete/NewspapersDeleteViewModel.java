package gui.screens.newspapers.newspapers_delete;

import domain.services.NewspaperServices;
import gui.screens.common.ScreenConstants;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Newspaper;

public class NewspapersDeleteViewModel {

    private final NewspaperServices servicesNewspapers;
    private final ObjectProperty<NewspapersDeleteState> state;

    @Inject
    public NewspapersDeleteViewModel(NewspaperServices servicesNewspapers) {
        this.servicesNewspapers = servicesNewspapers;
        state = new SimpleObjectProperty<>(new NewspapersDeleteState(null, null, null, null));
    }

    public ReadOnlyObjectProperty<NewspapersDeleteState> getState() {
        return state;
    }


    public void loadNewspapers() {
        servicesNewspapers.getNewspapers()
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isLeft())
                        state.set(new NewspapersDeleteState(either.getLeft(), null, null, null));
                    else {
                        state.set(new NewspapersDeleteState(null, null, either.get(), null));
                    }
                });
    }

    public void deleteNewspaper(Newspaper newspaper) {
        if (newspaper != null) {
            servicesNewspapers.deleteNewspaper(newspaper)
                    .subscribeOn(Schedulers.single())
                    .subscribe(either -> {
                        if (either.isLeft()) {
                            state.set(new NewspapersDeleteState(either.getLeft(), null, null, null));
                        } else if (Boolean.TRUE.equals(either.get())) {
                            state.set(new NewspapersDeleteState(null, ScreenConstants.OPERATION_DONE, null, newspaper));
                        }
                    });
        } else {
            state.set(new NewspapersDeleteState(ScreenConstants.CHOOSE_NEWSPAPER, null, null, null));
        }
    }

    public void cleanState() {
        state.set(new NewspapersDeleteState(null, null, null, null));
    }
}
