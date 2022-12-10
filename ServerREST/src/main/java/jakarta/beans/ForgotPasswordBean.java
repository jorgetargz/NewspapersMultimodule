package jakarta.beans;


import domain.services.ServicesLogin;
import jakarta.beans.utils.MailSender;
import jakarta.beans.utils.VerificationCode;
import jakarta.common.Constantes;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.extern.log4j.Log4j2;
import modelo.Secret;

import java.io.Serializable;
import java.time.LocalDateTime;

@Named
@SessionScoped
@Log4j2
public class ForgotPasswordBean implements Serializable {


    private String code;

    private String email;
    private String newPassword;
    private String newPasswordConfirmation;

    private String changePasswordError;

    private final MailSender mailSender;
    private final VerificationCode verificationCode;
    private final ServicesLogin servicesLogin;

    @Inject
    public ForgotPasswordBean(MailSender mailSender, VerificationCode verificationCode, ServicesLogin servicesLogin) {
        this.mailSender = mailSender;
        this.verificationCode = verificationCode;
        this.servicesLogin = servicesLogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }

    public void setNewPasswordConfirmation(String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }

    public String getChangePasswordError() {
        return changePasswordError;
    }

    public void setChangePasswordError(String changePasswordError) {
        this.changePasswordError = changePasswordError;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String sendVerificationMail() {
        if (email != null) {
            try {
                String secretCode = verificationCode.generate();
                mailSender.generateAndSendEmail(email,
                        String.format(Constantes.FORGOT_PASSWORD_MAIL_CONTENT, secretCode),
                        Constantes.FORGOT_PASSWORD);
                LocalDateTime codeExpirationDate = LocalDateTime.now().plusMinutes(5);
                Secret secret = new Secret(secretCode, codeExpirationDate, null, email);
                servicesLogin.updateSecretByMail(secret);
                return Constantes.EMAIL_SEND_REDIRECT;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Constantes.SERVER_ERROR_REDIRECT;
            }
        }
        return null;
    }

    public String changePassword() {
        Secret secret = servicesLogin.getSecret(code);
        if (secret != null && secret.getCodeExpirationDate().isAfter(LocalDateTime.now())) {
            if (secret.getCode().equals(code)) {
                if (servicesLogin.changePassword(newPassword, secret.getEmail())) {
                    return Constantes.CHANGE_PASSWORD_SUCCESS_REDIRECT;
                } else {
                    changePasswordError = Constantes.ERROR_CHANGING_PASSWORD;
                    return Constantes.CHANGE_PASSWORD_ERROR_REDIRECT;
                }
            } else {
                changePasswordError = Constantes.INVALID_VERIFICATION_CODE;
                return Constantes.CHANGE_PASSWORD_ERROR_REDIRECT;
            }
        } else {
            changePasswordError = Constantes.VERIFICATION_CODE_EXPIRED;
            return Constantes.CHANGE_PASSWORD_ERROR_REDIRECT;
        }
    }
}
