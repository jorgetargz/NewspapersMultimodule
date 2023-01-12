package jakarta.common;

public class Constantes {

    private Constantes() {
    }


    public static final String MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String MAIL_SMTP_SSL_TRUST = "mail.smtp.ssl.trust";
    public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String TEXT_HTML = "text/html";
    public static final String SMTP = "smtp";

    public static final int VERIFICATION_CODE_LENGTH = 32;
    public static final String FORGOT_PASSWORD = "Forgot Password";
    public static final String FORGOT_PASSWORD_MAIL_CONTENT = """
            <html>
                <body>
                    <h1>Forgot password</h1>
                    <img src="http://localhost:8080/NewspapersREST/resources/images/logo.gif" alt="Logo" width="200" height="200">
                    <p>Click on the link below to recover your account</p>
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
    public static final String SUPERADO_EL_MAXIMO_DE_PETICIONES = "Se ha superado el número máximo de peticiones permitidas \nen el periodo de tiempo";
    public static final String TOKEN_IN_BLACK_LIST = "Token in black list";

    public static final int EXPIRATION_TIME_MINUTES_IN_THE_FUTURE = 5;
    public static final int NOT_BEFORE_MINUTES_IN_THE_PAST = 2;
    public static final int SECONDS_OF_ALLOWED_CLOCK_SKEW = 30;

    public static final String BASIC = "Basic";
    public static final String WHITE_SPACE = " ";
    public static final String ERROR_LOGIN = "LOGIN_ERROR";
    public static final String EMAIL_IS_NOT_VERIFIED = "Email is not verified";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String LOGIN_REQUIRED = "Login required probably because of expired jwt";
    public static final String BEARER = "Bearer";
    public static final String BEARER_AUTH = "Bearer %s";
    public static final String TOKEN_EXPIRED = "Token expired";
    public static final String TRUE = "true";
    public static final String NEWSPAPERS_API = "NewspapersAPI";
    public static final String CLIENTS = "Clients";
    public static final String API_AUTH = "API Auth";
    public static final String NOMBRE = "Nombre";
    public static final String ROLES = "Roles";
    public static final String KEY_ID = "NewspapersAPI";
}
