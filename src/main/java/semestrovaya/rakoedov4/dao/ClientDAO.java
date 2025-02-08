package semestrovaya.rakoedov4.dao;

import semestrovaya.rakoedov4.entity.Client;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDAO {
    private final DataSource dataSource;

    public ClientDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public void create(Client client) throws SQLException {
        String sql = "INSERT INTO client (first_name, last_name, phone, email, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, client.getFirst_name());
            preparedStatement.setString(2, client.getLast_name());
            preparedStatement.setString(3, client.getPhone());
            preparedStatement.setString(4, client.getEmail());
            preparedStatement.setString(5, client.getPassword());
            preparedStatement.executeUpdate();
        }
    }
    public Optional<Client> findById(int id) throws SQLException {
        String sql = "SELECT * FROM client WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Client(
                        resultSet.getInt("user_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                ));
            }
        }
        return Optional.empty();
    }
    public void update(Client client) throws SQLException {
        String sql = "UPDATE client SET first_name = ?, last_name = ?, phone = ?, email = ?, password = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, client.getFirst_name());
            preparedStatement.setString(2, client.getLast_name());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setString(4, client.getPhone());
            preparedStatement.setString(5, client.getPassword());
            preparedStatement.setInt(6, client.getUser_id());
            preparedStatement.executeUpdate();
        }
    }
    //Transactions
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM client WHERE user_id = ?";
        String deleteCartSql = "DELETE FROM cart WHERE client_id = ?";
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false); // Отключаем авто-коммит
            try (PreparedStatement deleteCartStmt = connection.prepareStatement(deleteCartSql);
                 PreparedStatement deleteClientStmt = connection.prepareStatement(sql)) {

                deleteCartStmt.setInt(1, id);
                deleteCartStmt.executeUpdate();

                deleteClientStmt.setInt(1, id);
                deleteClientStmt.executeUpdate();

                connection.commit();
            } catch (Exception e) {
                connection.rollback(); // Откат изменений в случае ошибки
                throw new SQLException("Ошибка при удалении клиента: " + e.getMessage(), e);
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }
    public List<Client> findAll() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * from client";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clients.add(new Client(
                        resultSet.getInt("user_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                ));
            }
        }
        return clients;
    }
    public Optional<Client> findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM client WHERE email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of( new Client(
                            resultSet.getInt("user_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("phone"),
                            email,
                            resultSet.getString("password")
                    ));
                }
            }
        }
        return Optional.empty();
    }
}
