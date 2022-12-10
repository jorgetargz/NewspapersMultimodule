package gui.screens.login;

import gui.screens.common.BaseScreenController;
import gui.screens.common.ScreenConstants;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.log4j.Log4j2;

import java.awt.*;
import java.io.IOException;

@Log4j2
public class LoginController extends BaseScreenController {

    private final LoginViewModel loginViewModel;

    @FXML
    private ImageView logo;
    @FXML
    private MFXTextField nameTxt;
    @FXML
    private MFXDatePicker datePicker;
    @FXML
    private MFXPasswordField passwordTxt;
    @FXML
    private MFXTextField emailTxt1;
    @FXML
    private MFXTextField usernameTxt;
    @FXML
    private MFXTextField txtUsername;
    @FXML
    private MFXPasswordField txtPass;

    @Inject
    public LoginController(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
    }

    public void initialize() {
        try (var inputStream = getClass().getResourceAsStream(ScreenConstants.MEDIA_LOGO_PNG)) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            logo.setImage(logoImage);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        loginViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.error() != null) {
                Platform.runLater(() -> {
                    this.getPrincipalController().showAlert(Alert.AlertType.ERROR, ScreenConstants.ERROR, newState.error());
                    loginViewModel.clenState();
                });
            }
            if (newState.reader() != null) {
                Platform.runLater(() -> {
                    this.getPrincipalController().setReader(newState.reader());
                    this.getPrincipalController().onLoginDone();
                });
            }
            if (newState.readerRegistered()) {
                Platform.runLater(() -> {
                    showEmailVerificationAlert();
                    loginViewModel.clenState();
                });
            }
        });
    }

    private static void showEmailVerificationAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(ScreenConstants.USER_REGISTERED);
        alert.setHeaderText(ScreenConstants.USER_REGISTERED);
        alert.setContentText(ScreenConstants.VERIFY_EMAIL);
        alert.getButtonTypes().clear();
        ButtonType buttonResend = new ButtonType("Go to verify email web");
        alert.getButtonTypes().add(buttonResend);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buttonResend) {
                try {
                    Desktop.getDesktop().browse(java.net.URI.create(ScreenConstants.SEND_VERIFICATION_EMAIL_XHTML));
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        });
    }

    @FXML
    private void doLogin() {
        String username = txtUsername.getText();
        String password = txtPass.getText();
        loginViewModel.doLogin(username, password);
    }

    @FXML
    private void doRegister() {
        loginViewModel.doRegister(nameTxt.getText(), datePicker.getValue(), emailTxt1.getText(), usernameTxt.getText(), passwordTxt.getText());
    }

    @FXML
    private void doForgotPassword() {
        try {
            Desktop.getDesktop().browse(java.net.URI.create(ScreenConstants.FORGOT_PASSWORD_XHTML));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
