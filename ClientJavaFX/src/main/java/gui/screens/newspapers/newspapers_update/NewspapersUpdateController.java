package gui.screens.newspapers.newspapers_update;

import gui.screens.common.BaseScreenController;
import gui.screens.common.ScreenConstants;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Newspaper;

import java.time.LocalDate;

public class NewspapersUpdateController extends BaseScreenController {

    private final NewspapersUpdateViewModel newspapersUpdateViewModel;
    @FXML
    private MFXTextField nameTxt;
    @FXML
    private MFXDatePicker releaseDatePicker;
    @FXML
    private Label title;
    @FXML
    private TableView<Newspaper> tableNewspapers;
    @FXML
    private TableColumn<Newspaper, Integer> columnId;
    @FXML
    private TableColumn<Newspaper, String> columnName;
    @FXML
    private TableColumn<Newspaper, LocalDate> columnPublishDate;

    @Inject
    public NewspapersUpdateController(NewspapersUpdateViewModel newspapersUpdateViewModel) {
        this.newspapersUpdateViewModel = newspapersUpdateViewModel;
    }

    public void initialize() {
        title.setText(ScreenConstants.NEWSPAPERS_UPDATE_CONTROL_PANEL);

        columnId.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.ID));
        columnName.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.NAME_NEWSPAPER));
        columnPublishDate.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.RELEASE_DATE));

        newspapersUpdateViewModel.loadNewspapers();

        newspapersUpdateViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.error() != null) {
                Platform.runLater(() -> {
                    getPrincipalController().showAlert(Alert.AlertType.ERROR, ScreenConstants.ERROR, newState.error());
                    newspapersUpdateViewModel.cleanState();
                });
            }
            if (newState.message() != null) {
                Platform.runLater(() -> {
                    getPrincipalController().showAlert(Alert.AlertType.INFORMATION, ScreenConstants.ERROR, newState.message());
                    newspapersUpdateViewModel.cleanState();
                });
            }
            if (newState.newspapers() != null) {
                Platform.runLater(() -> {
                    tableNewspapers.getItems().clear();
                    tableNewspapers.getItems().addAll(newState.newspapers());
                });
            }
            if (newState.updatedNewspaper() != null) {
                Platform.runLater(() -> {
                    tableNewspapers.getItems().removeIf(newspaper -> newspaper.getId() == (newState.updatedNewspaper().getId()));
                    tableNewspapers.getItems().add(newState.updatedNewspaper());
                });
            }
            if (newState.isLoading()) {
                this.getPrincipalController().getRootPane().setCursor(Cursor.WAIT);
            }
            if (newState.isLoaded()) {
                this.getPrincipalController().getRootPane().setCursor(Cursor.DEFAULT);
            }
        });
    }

    @FXML
    private void updateFields() {
        Newspaper newspaper = tableNewspapers.getSelectionModel().getSelectedItem();
        if (newspaper != null) {
            nameTxt.setText(newspaper.getNameNewspaper());
            releaseDatePicker.setValue(newspaper.getReleaseDate());
        }
    }

    @FXML
    private void updateNewspaper() {
        Newspaper newspaper = tableNewspapers.getSelectionModel().getSelectedItem();
        newspapersUpdateViewModel.updateNewspaper(nameTxt.getText(), releaseDatePicker.getValue(), newspaper);
    }
}
