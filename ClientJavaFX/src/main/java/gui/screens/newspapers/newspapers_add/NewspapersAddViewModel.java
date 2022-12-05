package gui.screens.newspapers.newspapers_add;

import domain.services.NewspaperServices;
import gui.screens.common.ScreenConstants;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Newspaper;

import java.time.LocalDate;

public class NewspapersAddViewModel {

    private final NewspaperServices servicesNewspapers;
    private final ObjectProperty<NewspapersAddState> state;

    @Inject
    public NewspapersAddViewModel(NewspaperServices servicesNewspapers) {
        this.servicesNewspapers = servicesNewspapers;
        state = new SimpleObjectProperty<>(new NewspapersAddState(null, null, null, null));
    }

    public ReadOnlyObjectProperty<NewspapersAddState> getState() {
        return state;
    }


    public void loadNewspapers() {
        servicesNewspapers.getNewspapers()
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isLeft())
                        state.set(new NewspapersAddState(either.getLeft(), null, null, null));
                    else {
                        state.set(new NewspapersAddState(null, null, either.get(), null));
                    }
                });
    }

    public void addNewspaper(String nameText, LocalDate releaseDatePickerValue) {
        if (nameText != null && !nameText.isEmpty()
                && releaseDatePickerValue != null) {
            Newspaper newspaper = new Newspaper(nameText, releaseDatePickerValue);
            servicesNewspapers.saveNewspaper(newspaper)
                    .subscribeOn(Schedulers.single())
                    .subscribe(either -> {
                        if (either.isLeft()) {
                            state.set(new NewspapersAddState(either.getLeft(), null, null, null));
                        } else {
                            state.set(new NewspapersAddState(null, ScreenConstants.OPERATION_DONE, null, either.get()));
                        }
                    });
        } else {
            state.set(new NewspapersAddState(ScreenConstants.FILL_ALL_THE_INPUTS, null, null, null));
        }
    }

    public void cleanState() {
        state.set(new NewspapersAddState(null, null, null, null));
    }
}

