package gui.screens.newspapers.newspapers_update;

import domain.services.NewspaperServices;
import gui.screens.common.ScreenConstants;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Newspaper;

import java.time.LocalDate;

public class NewspapersUpdateViewModel {

    private final NewspaperServices servicesNewspapers;
    private final ObjectProperty<NewspapersUpdateState> state;

    @Inject
    public NewspapersUpdateViewModel(NewspaperServices servicesNewspapers) {
        this.servicesNewspapers = servicesNewspapers;
        state = new SimpleObjectProperty<>(new NewspapersUpdateState(null, null, null, null, false, false));
    }

    public ReadOnlyObjectProperty<NewspapersUpdateState> getState() {
        return state;
    }


    public void loadNewspapers() {
        state.set(new NewspapersUpdateState(null, null, null, null, true, false));
        servicesNewspapers.getNewspapers()
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isLeft()) {
                        state.set(new NewspapersUpdateState(either.getLeft(), null, null, null, false, true));
                    } else {
                        state.set(new NewspapersUpdateState(null, null, either.get(), null, false, true));
                    }
                });
    }

    public void updateNewspaper(String nameText, LocalDate releaseDatePickerValue, Newspaper newspaper) {
        if (nameText != null && !nameText.isEmpty()
                && releaseDatePickerValue != null) {
            newspaper.setNameNewspaper(nameText);
            newspaper.setReleaseDate(releaseDatePickerValue);
            state.set(new NewspapersUpdateState(null, null, null, null, true, false));
            servicesNewspapers.updateNewspaper(newspaper)
                    .subscribeOn(Schedulers.single())
                    .subscribe(either -> {
                        if (either.isLeft()) {
                            state.set(new NewspapersUpdateState(either.getLeft(), null, null, null, false, true));
                        } else {
                            state.set(new NewspapersUpdateState(null, ScreenConstants.OPERATION_DONE, null, either.get(), false, true));
                        }
                    });
            loadNewspapers();
        } else {
            state.set(new NewspapersUpdateState(ScreenConstants.FILL_ALL_THE_INPUTS, null, null, null, false, true));
        }
    }

    public void cleanState() {
        state.set(new NewspapersUpdateState(null, null, null, null, false, false));
    }
}

