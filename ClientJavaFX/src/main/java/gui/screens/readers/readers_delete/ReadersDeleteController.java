package gui.screens.readers.readers_delete;

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
import modelo.Reader;

import java.time.LocalDate;

public class ReadersDeleteController extends BaseScreenController {


    private final ReadersDeleteViewModel readersListViewModel;
    @FXML
    private Label title;
    @FXML
    private TableView<Reader> tableReaders;
    @FXML
    private TableColumn<Reader, Integer> columnId;
    @FXML
    private TableColumn<Reader, String> columnName;
    @FXML
    private TableColumn<Reader, LocalDate> columnDateOfBirth;

    @Inject
    public ReadersDeleteController(ReadersDeleteViewModel readersListViewModel) {
        this.readersListViewModel = readersListViewModel;
    }

    public void initialize() {
        title.setText(ScreenConstants.READERS_DELETE_CONTROL_PANEL);

        columnId.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.ID));
        columnName.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.NAME));
        columnDateOfBirth.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.DATE_OF_BIRTH));

        readersListViewModel.loadReaders();

        readersListViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.error() != null) {
                Platform.runLater(() -> {
                    getPrincipalController().showAlert(Alert.AlertType.ERROR, ScreenConstants.ERROR, newState.error());
                    readersListViewModel.cleanState();
                });
            }
            if (newState.message() != null) {
                Platform.runLater(() -> {
                    getPrincipalController().showAlert(Alert.AlertType.INFORMATION, ScreenConstants.INFORMATION, newState.message());
                    readersListViewModel.cleanState();
                });
            }
            if (newState.readers() != null) {
                Platform.runLater(() -> {
                    tableReaders.getItems().clear();
                    tableReaders.getItems().addAll(newState.readers());
                });
            }
            if (newState.readerDeleted() != null) {
                Platform.runLater(() -> tableReaders.getItems().remove(newState.readerDeleted()));
            }
        });
    }

    @FXML
    private void deleteReader(ActionEvent actionEvent) {
        Reader reader = tableReaders.getSelectionModel().getSelectedItem();
        readersListViewModel.deleteReader(reader);
    }
}
