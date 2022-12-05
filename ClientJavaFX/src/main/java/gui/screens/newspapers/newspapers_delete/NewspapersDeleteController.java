package gui.screens.newspapers.newspapers_delete;

import gui.screens.common.BaseScreenController;
import gui.screens.common.ScreenConstants;
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

public class NewspapersDeleteController extends BaseScreenController {

    private final NewspapersDeleteViewModel newspapersDeleteViewModel;
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
    public NewspapersDeleteController(NewspapersDeleteViewModel newspapersDeleteViewModel) {
        this.newspapersDeleteViewModel = newspapersDeleteViewModel;
    }

    public void initialize() {
        title.setText(ScreenConstants.NEWSPAPERS_DELETE_CONTROL_PANEL);

        columnId.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.ID));
        columnName.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.NAME_NEWSPAPER));
        columnPublishDate.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.RELEASE_DATE));

        newspapersDeleteViewModel.loadNewspapers();

        newspapersDeleteViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.error() != null) {
                Platform.runLater(() -> {
                    getPrincipalController().showAlert(Alert.AlertType.ERROR, ScreenConstants.ERROR, newState.error());
                    newspapersDeleteViewModel.cleanState();
                });
            }
            if (newState.message() != null) {
                Platform.runLater(() -> {
                    getPrincipalController().showAlert(Alert.AlertType.INFORMATION, ScreenConstants.ERROR, newState.message());
                    newspapersDeleteViewModel.cleanState();
                });
            }
            if (newState.newspapers() != null) {
                Platform.runLater(() -> {
                    tableNewspapers.getItems().clear();
                    tableNewspapers.getItems().addAll(newState.newspapers());
                });
            }
            if (newState.newspaperDeleted() != null) {
                Platform.runLater(() -> tableNewspapers.getItems().remove(newState.newspaperDeleted()));
            }
        });
    }

    @FXML
    private void deleteNewspaper(ActionEvent actionEvent) {
        Newspaper newspaper = tableNewspapers.getSelectionModel().getSelectedItem();
        newspapersDeleteViewModel.deleteNewspaper(newspaper);
    }
}
