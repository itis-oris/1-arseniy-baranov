package semestrovaya.rakoedov4.service;

import semestrovaya.rakoedov4.dao.OrderDAO;
import semestrovaya.rakoedov4.entity.Order;

import java.sql.SQLException;
import java.util.List;

public class OrderService {
    OrderDAO orderDAO;
    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }
    public Order findById(int orderId) throws SQLException {
        return orderDAO.findById(orderId).orElse(null);
    }
    public int create(Order order) throws SQLException {
         return orderDAO.create(order);
    }
    public List<Order> findByClientId(int clientId) throws SQLException {
        return orderDAO.findByClientId(clientId);
    }
}
