package semestrovaya.rakoedov4.service;

import semestrovaya.rakoedov4.dao.CartDAO;
import semestrovaya.rakoedov4.entity.Cart;

import java.sql.SQLException;
import java.util.List;

public class CartService {
    CartDAO cartDAO;

    public CartService(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    public Cart findById(int id) {
        return cartDAO.findById(id).orElse(null);
    }

    public List<Cart> findByClientId(int clientId) throws SQLException {
        return cartDAO.findByClientId(clientId);
    }
    public void deleteById(int id) throws SQLException {
        cartDAO.deleteById(id);
    }
    public void deleteByClientId(int clientId) throws SQLException {
        cartDAO.deleteByClientId(clientId);
    }
    public void create(Cart cart) throws SQLException {
        cartDAO.create(cart);
    }
    public List<Cart> findAll() throws SQLException {
        return cartDAO.findAll();
    }
    public void removeFromCart(int clientId, int productId) {
        cartDAO.removeItem(clientId, productId);
    }
    public void addToCart(int clientId, int productId, int amount) throws SQLException {

        cartDAO.addToCart(clientId,productId, amount);
    }
    public Cart findByClientIdAndProductId(int clientId, int productId) throws SQLException {
        return cartDAO.findByClientIdAndProductId(clientId, productId).orElse(null);
    }

    public int getAmount(int clientId, int productId) throws SQLException {
        return cartDAO.getAmount(clientId,productId);
    }

}
