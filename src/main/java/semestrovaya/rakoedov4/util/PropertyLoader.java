package semestrovaya.rakoedov4.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    private static final String PROPERTIES_FILE = "db.properties";

    public static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = PropertyLoader.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new IllegalArgumentException("Файл свойств " + PROPERTIES_FILE + " не найден в classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки файла свойств: " + PROPERTIES_FILE, e);
        }
        return properties;
    }
}

