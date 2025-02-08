package semestrovaya.rakoedov4.dao;

import semestrovaya.rakoedov4.entity.Cart;
import semestrovaya.rakoedov4.entity.Product;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAO {

    private final DataSource dataSource;

    public ProductDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<Product> findById(int id) throws SQLException {
        String sql = "SELECT * FROM product WHERE product_id = ?";
        String base64Image = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                InputStream imageStream = resultSet.getBinaryStream("image");
                if (imageStream != null) {
                    byte[] imageBytes = imageStream.readAllBytes();
                    base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);

                }
                return Optional.of(new Product(
                        resultSet.getInt("product_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        base64Image
                ));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        String base64Image = null;
        String sql = "SELECT * FROM product";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                InputStream imageStream = resultSet.getBinaryStream("image");
                if (imageStream != null) {
                    byte[] imageBytes = imageStream.readAllBytes();
                    base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);
                }
                products.add(new Product(
                        resultSet.getInt("product_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        base64Image
                ));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}
