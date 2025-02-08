package semestrovaya.rakoedov4.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import semestrovaya.rakoedov4.dao.*;
import semestrovaya.rakoedov4.service.*;
import semestrovaya.rakoedov4.util.HikariCPDataSource;
import semestrovaya.rakoedov4.config.EncoderConfiguration;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        DataSource dataSource = HikariCPDataSource.getInstance().getDataSource();
        migrateFlyway(dataSource);

        EncoderConfiguration encoderConfiguration = new EncoderConfiguration();
        PasswordEncoder passwordEncoder = encoderConfiguration.bCryptPasswordEncoder();

        ClientDAO clientDAO = new ClientDAO(dataSource);
        ClientService clientService = new ClientService(clientDAO, passwordEncoder);

        ProductDAO productDAO = new ProductDAO(dataSource);
        ProductService productService = new ProductService(productDAO);

        CartDAO cartDAO = new CartDAO(dataSource);
        CartService cartService = new CartService(cartDAO);

        OrderDAO orderDAO = new OrderDAO(dataSource);
        OrderService orderService = new OrderService(orderDAO);

        ProductOrderDAO productOrderDAO = new ProductOrderDAO(dataSource);
        ProductOrderService productOrderService = new ProductOrderService(productOrderDAO);

        StageDAO stageDAO = new StageDAO(dataSource);
        StageService stageService = new StageService(stageDAO);

        ServletContext servletContext = servletContextEvent.getServletContext();

        servletContext.setAttribute("clientService", clientService);
        servletContext.setAttribute("productService", productService);
        servletContext.setAttribute("cartService", cartService);
        servletContext.setAttribute("orderService", orderService);
        servletContext.setAttribute("productOrderService", productOrderService);
        servletContext.setAttribute("stageService", stageService);

    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HikariCPDataSource.getInstance().close();
    }

    public void migrateFlyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .baselineOnMigrate(true) // если уже есть таблицы
                .load();
        flyway.migrate();
    }
}
