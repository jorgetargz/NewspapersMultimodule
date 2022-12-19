package jakarta.common;

public class Constantes {

    public static final String NO_SE_HA_PODIDO_AUTENTICAR_AL_USUARIO = "No se ha podido autenticar al usuario";

    private Constantes() {
    }

    public static final String MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String MAIL_SMTP_SSL_TRUST = "mail.smtp.ssl.trust";
    public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String TEXT_HTML = "text/html";
    public static final String SMTP = "smtp";

    public static final int VERIFICATION_CODE_LENGHT = 32;
    public static final String FORGOT_PASSWORD = "Forgot Password";
    public static final String FORGOT_PASSWORD_MAIL_CONTENT = """
            <html>
                <body>
                    <h1>Forgot password</h1>
                    <img src="http://localhost:8080/NewspapersREST/resources/images/logo.gif" alt="Logo" width="200" height="200">
                    <p>Click on the link below to reccover your account</p>
                    <a href="http://localhost:8080/NewspapersREST/changePassword.xhtml?code=%s">Change password</a>
                </body>
            </html>
            """;
    public static final String VERIFICATION_MAIL = "Verification mail";
    public static final String VERIFICATION_MAIL_CONTENT = """
            <html>
                <body>
                    <h1>Verification code</h1>
                    <img src="http://localhost:8080/NewspapersREST/resources/images/logo.gif" alt="Logo" width="200" height="200">
                    <p>Click on the link below to verify your account</p>
                    <a href="http://localhost:8080/NewspapersREST/verifyEmail.xhtml?code=%s">Verify</a>
                </body>
            </html>
            """;

    public static final String EMAIL_SEND_REDIRECT = "/emailSend.xhtml?faces-redirect=true";
    public static final String SERVER_ERROR_REDIRECT = "/serverError.xhtml?faces-redirect=true";

    public static final String CHANGE_PASSWORD_SUCCESS_REDIRECT = "/changePasswordSuccess.xhtml?faces-redirect=true";
    public static final String CHANGE_PASSWORD_ERROR_REDIRECT = "/changePasswordError.xhtml?faces-redirect=true";
    public static final String VERIFY_EMAIL_SUCCESS_REDIRECT = "/verifyEmailSuccess.xhtml?faces-redirect=true";
    public static final String VERIFY_EMAIL_ERROR_REDIRECT = "/verifyEmailError.xhtml?faces-redirect=true";

    public static final String ERROR_CHANGING_PASSWORD = "Error changing password";
    public static final String INVALID_VERIFICATION_CODE = "Invalid verification code";
    public static final String VERIFICATION_CODE_EXPIRED = "Verification code expired";

    public static final String CREDENTIAL = "CREDENTIAL";
    public static final String BASIC = "Basic";
    public static final String WHITE_SPACE = " ";
}
