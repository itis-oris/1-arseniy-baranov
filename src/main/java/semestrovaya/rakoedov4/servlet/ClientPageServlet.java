package semestrovaya.rakoedov4.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import semestrovaya.rakoedov4.entity.Client;
import semestrovaya.rakoedov4.entity.Order;
import semestrovaya.rakoedov4.entity.Product;
import semestrovaya.rakoedov4.entity.ProductOrder;
import semestrovaya.rakoedov4.service.OrderService;
import semestrovaya.rakoedov4.service.ProductOrderService;
import semestrovaya.rakoedov4.service.ProductService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/clientPage")
public class ClientPageServlet extends HttpServlet {

    OrderService orderService;
    ProductOrderService productOrderService;
    ProductService productService;

    public void init() throws ServletException {
        orderService = (OrderService) getServletContext().getAttribute("orderService");
        productOrderService = (ProductOrderService) getServletContext().getAttribute("productOrderService");
        productService = (ProductService) getServletContext().getAttribute("productService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");
        if (client == null) {
            response.sendRedirect("/login");
        }
        List<Product> products;
        try {
            products = productService.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Map<Integer, Product> productMap = new HashMap<>();
        for (Product product : products) {
            productMap.put(product.getProduct_id(), product);


            List<Order> orders;
            try {
                orders = orderService.findByClientId(client.getUser_id());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            for (Order order : orders) {
                List<ProductOrder> productOrders = null;
                try {
                    productOrders = productOrderService.findByOrderId(order.getOrder_id());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                order.setProductOrders(productOrders);
            }
            request.setAttribute("ordersByClient", orders);
            request.setAttribute("client", client);
            request.setAttribute("products", products);
            request.setAttribute("productMap", productMap);


            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/clientPage.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
