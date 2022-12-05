package gui.screens.readers.readers_update;

import gui.screens.common.BaseScreenController;
import gui.screens.common.ScreenConstants;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
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
import javafx.scene.input.MouseEvent;
import modelo.Reader;

import java.time.LocalDate;

public class ReadersUpdateController extends BaseScreenController {


    private final ReadersUpdateViewModel readersUpdateViewModel;
    @FXML
    private MFXTextField nameTxt;
    @FXML
    private MFXDatePicker datePicker;
    @FXML
    private MFXPasswordField passwordTxt;
    @FXML
    private MFXTextField usernameTxt;
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
    public ReadersUpdateController(ReadersUpdateViewModel readersUpdateViewModel) {
        this.readersUpdateViewModel = readersUpdateViewModel;
    }

    public void initialize() {
        title.setText(ScreenConstants.UPDATE_READERS_CONTROL_PANEL);

        columnId.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.ID));
        columnName.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.NAME));
        columnDateOfBirth.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.DATE_OF_BIRTH));
        usernameTxt.setEditable(false);

        readersUpdateViewModel.loadReaders();

        readersUpdateViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.error() != null) {
                Platform.runLater(() -> {
                    getPrincipalController().showAlert(Alert.AlertType.ERROR, ScreenConstants.ERROR, newState.error());
                    readersUpdateViewModel.cleanState();
                });
            }
            if (newState.message() != null) {
                Platform.runLater(() -> {
                    getPrincipalController().showAlert(Alert.AlertType.INFORMATION, ScreenConstants.INFORMATION, newState.message());
                    readersUpdateViewModel.cleanState();
                });
            }
            if (newState.updatedReader() != null) {
                Platform.runLater(() -> {
                    tableReaders.getItems().removeIf(reader -> reader.getId() == newState.updatedReader().getId());
                    tableReaders.getItems().add(newState.updatedReader());
                });
            }
            if (newState.readers() != null) {
                Platform.runLater(() -> {
                    tableReaders.getItems().clear();
                    tableReaders.getItems().addAll(newState.readers());
                });
            }
        });
    }

    @FXML
    private void updateFields(MouseEvent mouseEvent) {
        Reader reader = tableReaders.getSelectionModel().getSelectedItem();
        if (reader != null) {
            usernameTxt.setText(reader.getLogin().getUsername());
            nameTxt.setText(reader.getName());
            datePicker.setValue(reader.getDateOfBirth());
            passwordTxt.setText(ScreenConstants.EMPTY_STRING);
        }
    }

    @FXML
    private void updateReader(ActionEvent actionEvent) {
        Reader reader = tableReaders.getSelectionModel().getSelectedItem();
        readersUpdateViewModel.updateRedaer(reader, nameTxt.getText(), datePicker.getValue(), passwordTxt.getText());
    }
}
