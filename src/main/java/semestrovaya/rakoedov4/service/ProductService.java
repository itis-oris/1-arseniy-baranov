package semestrovaya.rakoedov4.service;

import semestrovaya.rakoedov4.dao.ProductDAO;
import semestrovaya.rakoedov4.entity.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductService {

    ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public Product findById(int id) throws SQLException {
        return productDAO.findById(id).orElse(null);
    }

    public List<Product> findAll() throws SQLException {
        return productDAO.findAll();
    }
}
