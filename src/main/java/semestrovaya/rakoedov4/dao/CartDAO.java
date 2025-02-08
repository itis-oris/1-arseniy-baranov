package semestrovaya.rakoedov4.dao;

import semestrovaya.rakoedov4.entity.Cart;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartDAO {
    private final DataSource dataSource;

    public CartDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<Cart> findById(int id) {
        String sql = "SELECT * FROM cart WHERE cart_id= ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Cart(
                        resultSet.getInt("cart_id"),
                        resultSet.getInt("client_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("amount")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public List<Cart> findByClientId(int clientId) throws SQLException {
        String sql = "SELECT * FROM cart WHERE client_id= ?";
        List<Cart> carts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carts.add(new Cart(
                        resultSet.getInt("cart_id"),
                        resultSet.getInt("client_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("amount")
                ));
            }
        }
        return carts;
    }
    public void deleteById(int id) {
        String sql = "DELETE FROM cart WHERE cart_id= ?";
        try ( Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteByClientId(int clientId) throws SQLException {
        String sql = "DELETE FROM cart WHERE client_id= ?";
        try ( Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, clientId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void create(Cart cart) throws SQLException {
        String sql = "INSERT INTO cart ( client_id, product_id, amount) VALUES (?, ?, ?)";
        try ( Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, cart.getClient_id());
            preparedStatement.setInt(2, cart.getProduct_id());
            preparedStatement.setInt(3, cart.getAmount());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public List<Cart> findAll() throws SQLException {
        List<Cart> carts = new ArrayList<>();
        String sql = "SELECT * FROM cart";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carts.add(new Cart(
                        resultSet.getInt("cart_id"),
                        resultSet.getInt("client_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("amount")
                ));
            }
        }
        return carts;
    }
    public void removeItem(int clientId, int productId) {
        String sql = "DELETE FROM cart WHERE client_id = ? AND product_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, clientId);
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении товара из корзины", e);
        }
    }
    public void addToCart(int clientId, int productId, int amount_p) throws SQLException {
        // Запрос на добавление количества товара
        String sql = "UPDATE cart SET amount = amount + ? WHERE client_id = ? AND product_id = ?";
        System.out.println(sql);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, amount_p);
            preparedStatement.setInt(2, clientId);
            preparedStatement.setInt(3, productId);
            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println(clientId +""+ productId+"" +amount_p);
            if (rowsUpdated > 0) {
                System.out.println("Количество товара успешно обновлено в корзине");
            } else {
                System.out.println("Не удалось обновить количество товара в корзине");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public int getAmount(int clientId, int productId) throws SQLException {
        int amount = 0;
        String sql = "SELECT amount FROM cart WHERE client_id = ? AND product_id = ?"   ;
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, clientId);
            preparedStatement.setInt(2, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                amount = resultSet.getInt("amount");
            }
        }catch (SQLException e){
            throw new RuntimeException("Ошибка при нахождении количества товара в корзине",e);
        }
        return amount;
    }
    public Optional<Cart> findByClientIdAndProductId(int clientId, int product_id) throws SQLException {
        String sql = "SELECT * FROM cart WHERE client_id= ? AND product_id= ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, clientId);
            preparedStatement.setInt(2, product_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Cart(
                        resultSet.getInt("cart_id"),
                        resultSet.getInt("client_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("amount")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }


}
