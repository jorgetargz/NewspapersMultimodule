package gui.screens.newspapers.newspapers_add;

import gui.screens.common.BaseScreenController;
import gui.screens.common.ScreenConstants;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Newspaper;

import java.time.LocalDate;

public class NewspapersAddController extends BaseScreenController {

    private final NewspapersAddViewModel newspapersAddViewModel;
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
    public NewspapersAddController(NewspapersAddViewModel newspapersAddViewModel) {
        this.newspapersAddViewModel = newspapersAddViewModel;
    }

    public void initialize() {
        title.setText(ScreenConstants.NEWSPAPERS_ADD_CONTROL_PANEL);

        columnId.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.ID));
        columnName.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.NAME_NEWSPAPER));
        columnPublishDate.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.RELEASE_DATE));

        newspapersAddViewModel.loadNewspapers();

        newspapersAddViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.error() != null) {
                Platform.runLater(() -> {
                    getPrincipalController().showAlert(Alert.AlertType.ERROR, ScreenConstants.ERROR, newState.error());
                    newspapersAddViewModel.cleanState();
                });
            }
            if (newState.message() != null) {
                Platform.runLater(() -> {
                    getPrincipalController().showAlert(Alert.AlertType.INFORMATION, ScreenConstants.ERROR, newState.message());
                    newspapersAddViewModel.cleanState();
                });
            }
            if (newState.newspapers() != null) {
                Platform.runLater(() -> {
                    tableNewspapers.getItems().clear();
                    tableNewspapers.getItems().addAll(newState.newspapers());
                });
            }
            if (newState.newNewspaper() != null) {
                Platform.runLater(() -> tableNewspapers.getItems().add(newState.newNewspaper()));
            }
        });
    }

    @FXML
    private void addNewspaper(ActionEvent actionEvent) {
        newspapersAddViewModel.addNewspaper(nameTxt.getText(), releaseDatePicker.getValue());
    }
}
