package semestrovaya.rakoedov4.service;

import semestrovaya.rakoedov4.dao.ProductOrderDAO;
import semestrovaya.rakoedov4.entity.Cart;
import semestrovaya.rakoedov4.entity.Order;
import semestrovaya.rakoedov4.entity.Product;
import semestrovaya.rakoedov4.entity.ProductOrder;

import java.sql.SQLException;
import java.util.List;

public class ProductOrderService {

    ProductOrderDAO productOrderDAO;

    public ProductOrderService(ProductOrderDAO productOrderDAO) {
        this.productOrderDAO = productOrderDAO;
    }
    public void create(Cart cart, Order order, Product product) throws SQLException {
        productOrderDAO.create(cart,order, product);
    }
    public List<ProductOrder> findByOrderId(int orderId) throws SQLException {
        return productOrderDAO.findByOrderId(orderId);
    }
}
