package semestrovaya.rakoedov4.dao;

import semestrovaya.rakoedov4.entity.Cart;
import semestrovaya.rakoedov4.entity.Order;
import semestrovaya.rakoedov4.entity.Product;
import semestrovaya.rakoedov4.entity.ProductOrder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductOrderDAO {
    private final DataSource dataSource;
    public ProductOrderDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public void create(Cart cart, Order order, Product product) throws SQLException {
        String sql = "INSERT INTO product_order (order_id, product_id, amount, total_price) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,order.getOrder_id());
            preparedStatement.setInt(2,cart.getProduct_id());
            preparedStatement.setInt(3, cart.getAmount());
            preparedStatement.setDouble(4, product.getPrice()*cart.getAmount());
            preparedStatement.executeUpdate();
        }
    }
    public List<ProductOrder> findByOrderId(int orderId) throws SQLException {
        List<ProductOrder> productOrders = new ArrayList<>();
        String sql = "SELECT * FROM product_order WHERE order_id = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productOrders.add(new ProductOrder(
                        resultSet.getInt("po_id"),
                        resultSet.getInt("order_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("amount"),
                        resultSet.getDouble("total_price")
                ));
            }
        }
        return productOrders;
    }
}
