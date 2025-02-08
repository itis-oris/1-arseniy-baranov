package semestrovaya.rakoedov4.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariCPDataSource {
    private static HikariCPDataSource instance = null;
    private final HikariDataSource dataSource;

    private HikariCPDataSource() {
        try {
            Properties properties = PropertyLoader.loadProperties();
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(properties.getProperty("jdbc.url"));
            hikariConfig.setUsername(properties.getProperty("jdbc.username"));
            hikariConfig.setPassword(properties.getProperty("jdbc.password"));
            hikariConfig.setDriverClassName(properties.getProperty("jdbc.driverClassName"));

            hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty("hikari.maximumPoolSize")));
            this.dataSource = new HikariDataSource(hikariConfig);
            System.out.println("HikariCP DataSource для PostgreSQL успешно создан.");

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при инициализации HikariCP DataSource для PostgreSQL.", e);
        }
    }

    public static HikariCPDataSource getInstance() {

        if (instance == null) {
            synchronized (HikariCPDataSource.class) {
                if (instance == null) {
                    instance = new HikariCPDataSource();
                }
            }
        }
        return instance;
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public synchronized void releaseConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
    public void close() {
        if (dataSource != null) {
            dataSource.close();
            System.out.println("HikariCP DataSource закрыт.");
        }
    }
}
