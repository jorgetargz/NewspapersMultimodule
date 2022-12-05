package configuration;

import configuration.common.Constantes;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.Properties;

@Singleton
@Getter
@Log4j2
public class Configuration {

    private String url;
    private String user;
    private String password;
    private String driver;

    private String mailHost;
    private String mailPort;
    private String mailAuth;
    private String mailStarttls;
    private String mailUsername;
    private String mailPassword;


    public Configuration() {
        try {
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream(Constantes.CONFIG_YAML_PATH));

            this.url = properties.getProperty(Constantes.URL);
            this.password = properties.getProperty(Constantes.PASSWORD);
            this.user = properties.getProperty(Constantes.USER);
            this.driver = properties.getProperty(Constantes.DRIVER);

            this.mailHost = properties.getProperty(Constantes.SMTP_HOST);
            this.mailPort = properties.getProperty(Constantes.SMTP_PORT);
            this.mailAuth = properties.getProperty(Constantes.SMTP_AUTH);
            this.mailStarttls = properties.getProperty(Constantes.SMTP_STARTTLS);
            this.mailUsername = properties.getProperty(Constantes.SMTP_USERNAME);
            this.mailPassword = properties.getProperty(Constantes.SMTP_PASSWORD);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

}
