package gui.screens.readers.readers_add;

import gui.screens.common.BaseScreenController;
import gui.screens.common.ScreenConstants;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Reader;

import java.time.LocalDate;

public class ReadersAddController extends BaseScreenController {


    private final ReadersAddViewModel readersAddViewModel;
    @FXML
    private MFXTextField nameTxt;
    @FXML
    private MFXDatePicker datePicker;
    @FXML
    private MFXPasswordField passwordTxt;
    @FXML
    private MFXTextField usernameTxt;
    @FXML
    private MFXTextField emailTxt;
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
    public ReadersAddController(ReadersAddViewModel readersAddViewModel) {
        this.readersAddViewModel = readersAddViewModel;
    }

    public void initialize() {
        title.setText(ScreenConstants.ADD_READERS_CONTROL_PANEL);

        columnId.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.ID));
        columnName.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.NAME));
        columnDateOfBirth.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.DATE_OF_BIRTH));

        readersAddViewModel.loadReaders();

        readersAddViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.error() != null) {
                Platform.runLater(() -> {
                    getPrincipalController().showAlert(Alert.AlertType.ERROR, ScreenConstants.ERROR, newState.error());
                    readersAddViewModel.cleanState();
                });
            }
            if (newState.message() != null) {
                Platform.runLater(() -> {
                    getPrincipalController().showAlert(Alert.AlertType.INFORMATION, ScreenConstants.INFORMATION, newState.message());
                    readersAddViewModel.cleanState();
                });
            }
            if (newState.newReader() != null) {
                Platform.runLater(() -> tableReaders.getItems().add(newState.newReader()));
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
    private void addReader() {
        readersAddViewModel.addReader(nameTxt.getText(), datePicker.getValue(), usernameTxt.getText(), passwordTxt.getText(), emailTxt.getText());
    }
}
