package gui.screens.newspapers.newspapers_list;

import gui.screens.common.BaseScreenController;
import gui.screens.common.ScreenConstants;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Newspaper;

import java.time.LocalDate;

public class NewspapersListController extends BaseScreenController {

    private final NewspapersListViewModel newspapersListViewModel;
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
    public NewspapersListController(NewspapersListViewModel newspapersListViewModel) {
        this.newspapersListViewModel = newspapersListViewModel;
    }

    public void initialize() {
        title.setText(ScreenConstants.NEWSPAPERS_LIST_CONTROL_PANEL);

        columnId.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.ID));
        columnName.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.NAME_NEWSPAPER));
        columnPublishDate.setCellValueFactory(new PropertyValueFactory<>(ScreenConstants.RELEASE_DATE));

        newspapersListViewModel.loadNewspapers();

        newspapersListViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.error() != null) {
                Platform.runLater(() -> {
                    getPrincipalController().showAlert(Alert.AlertType.ERROR, ScreenConstants.ERROR, newState.error());
                    newspapersListViewModel.cleanState();
                });
            }
            if (newState.newspapers() != null) {
                Platform.runLater(() -> {
                    tableNewspapers.getItems().clear();
                    tableNewspapers.getItems().addAll(newState.newspapers());
                });
            }
        });
    }
}