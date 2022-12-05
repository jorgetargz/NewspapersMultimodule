package dao.newspapers_api.config;

import dao.common.Constantes;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Properties;

@Getter
@Log4j2
@Singleton
public class ConfigNewspapersAPI {

    private String baseUrl = null;

    public ConfigNewspapersAPI() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(Constantes.CONFIG_NEWSPAPERS_API_PATH));
            this.baseUrl = (String) properties.get(Constantes.BASE_URL);
            log.info(Constantes.API_BASE_URL, this.baseUrl);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
