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
public class VerifyEmailBean implements Serializable {

    private String code;

    private String email;
    private String username;
    private String password;

    private String verifyEmailError;

    private MailSender mailSender;
    private VerificationCode verificationCode;
    private ServicesLogin servicesLogin;

    @Inject
    public VerifyEmailBean(MailSender mailSender, VerificationCode verificationCode, ServicesLogin servicesLogin) {
        this.mailSender = mailSender;
        this.verificationCode = verificationCode;
        this.servicesLogin = servicesLogin;
    }

    public VerifyEmailBean() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVerifyEmailError() {
        return verifyEmailError;
    }

    public void setVerifyEmailError(String verifyEmailError) {
        this.verifyEmailError = verifyEmailError;
    }

    public String sendVerificationMail() {
        if (email == null) {
            Secret secretDB = servicesLogin.getSecret(code);
            email = secretDB.getEmail();
            username = secretDB.getUsername();
        } else {
            if (servicesLogin.checkCredentials(username, password)) {
                try {
                    String secretCode = verificationCode.generate();
                    mailSender.generateAndSendEmail(email,
                            String.format(Constantes.VERIFICATION_MAIL_CONTENT, secretCode),
                            Constantes.VERIFICATION_MAIL);
                    LocalDateTime codeExpirationDate = LocalDateTime.now().plusMinutes(5);
                    Secret secret = new Secret(secretCode, codeExpirationDate, username, email);
                    servicesLogin.updateSecretByUsername(secret);
                    return Constantes.EMAIL_SEND_REDIRECT;
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    return Constantes.SERVER_ERROR_REDIRECT;
                }
            }
        }
        return null;
    }

    public String verifyEmail() {
        Secret secret = servicesLogin.getSecret(code);
        if (secret != null && secret.getCodeExpirationDate().isAfter(LocalDateTime.now())) {
            if (secret.getCode().equals(code)) {
                if (servicesLogin.saveVerifiedMail(secret.getUsername(), secret.getEmail())) {
                    return Constantes.VERIFY_EMAIL_SUCCESS_REDIRECT;
                } else {
                    verifyEmailError = Constantes.ERROR_VERIFYING_EMAIL;
                    return Constantes.VERIFY_EMAIL_ERROR_REDIRECT;
                }
            } else {
                verifyEmailError = Constantes.INVALID_VERIFICATION_CODE;
                return Constantes.VERIFY_EMAIL_ERROR_REDIRECT;
            }
        } else {
            verifyEmailError = Constantes.VERIFICATION_CODE_EXPIRED;
            return Constantes.VERIFY_EMAIL_ERROR_REDIRECT;
        }
    }
}
