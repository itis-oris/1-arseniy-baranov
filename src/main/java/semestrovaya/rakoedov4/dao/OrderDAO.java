package semestrovaya.rakoedov4.dao;

import semestrovaya.rakoedov4.entity.Order;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDAO {
    private final DataSource dataSource;
    public OrderDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public Optional<Order> findById(int id) throws SQLException {
        String sql = "SELECT * FROM orders WHERE product_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Order(
                        resultSet.getInt("order_id"),
                        resultSet.getInt("client_id"),
                        resultSet.getInt("stage_id"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getTimestamp("updated_at"),
                        resultSet.getDouble("total_price")
                ));
            }
        }
        return Optional.empty();
    }
    public int create(Order order) throws SQLException {
        String sql = "INSERT INTO orders (client_id, stage_id, created_at, updated_at, total_price) VALUES (?, ?, ?, ?, ?) RETURNING order_id";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,order.getClient_id());
            preparedStatement.setInt(2,order.getStage_id());
            preparedStatement.setTimestamp(3,new Timestamp(order.getCreated_at().getTime()));
            preparedStatement.setTimestamp(4,new Timestamp(order.getUpdated_at().getTime()));
            preparedStatement.setDouble(5,order.getTotal_price());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("order_id"); // Возвращаем сгенерированный идентификатор
            } else {
                throw new SQLException("Failed to retrieve order ID."); // Ошибка, если результат пустой
            }

        }
    }
    public List<Order> findByClientId(int clientId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE client_id = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(new Order(
                        resultSet.getInt("order_id"),
                        resultSet.getInt("client_id"),
                        resultSet.getInt("stage_id"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getTimestamp("updated_at"),
                        resultSet.getDouble("total_price")
                ));
            }
        }
        return orders;
    }
}
