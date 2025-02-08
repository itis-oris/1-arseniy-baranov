package semestrovaya.rakoedov4.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import semestrovaya.rakoedov4.entity.*;
import semestrovaya.rakoedov4.service.*;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    OrderService orderService;
    CartService cartService;
    ProductService productService;
    ProductOrderService productOrderService;
    StageService stageService;


    public void init() throws ServletException {

        this.cartService = (CartService) getServletContext().getAttribute("cartService");
        this.orderService = (OrderService) getServletContext().getAttribute("orderService");
        this.productService = (ProductService) getServletContext().getAttribute("productService");
        this.productOrderService = (ProductOrderService) getServletContext().getAttribute("productOrderService");
        this.stageService = (StageService) getServletContext().getAttribute("stageService");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");
        List<Product> products;
        List<Stage> stages;
        try {
            products = productService.findAll();
            stages = stageService.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Map<Integer, String> productMap = new HashMap<>();
        Map<Integer, Stage> stageMap = new HashMap<>();
        for (Product product : products) {
            productMap.put(product.getProduct_id(), product.getName());
        }
        for (Stage stage : stages) {
            stageMap.put(stage.getStage_id(), stage);
        }

        if (client == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            List<Order> orders = orderService.findByClientId(client.getUser_id());
            for (Order order : orders) {
                List<ProductOrder> productOrders = productOrderService.findByOrderId(order.getOrder_id());
                order.setProductOrders(productOrders);
            }

            request.setAttribute("ordersByClient", orders);
            request.setAttribute("client", client);
            request.setAttribute("productMap", productMap);
            request.setAttribute("stageMap", stageMap);

            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/order.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при получении заказов");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");
        if (client == null) {
            request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
        }
        String totalSum1 = request.getParameter("totalSum");

        double totalSum = Double.parseDouble(totalSum1);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Order order = new Order(client.getUser_id(), 1, timestamp, timestamp, totalSum);


        String action = request.getParameter("action");

        if ("makeOrder".equals(action)) {
            try {
                int orderId = orderService.create(order);
                order.setOrder_id(orderId);
                List<Cart> cartItems = cartService.findByClientId(client.getUser_id());
                List<Product> productItems = productService.findAll();
                for (Cart cartItem : cartItems) {
                    productOrderService.create(cartItem, order, productItems.get(cartItem.getProduct_id() - 1));
                }
                cartService.deleteByClientId(client.getUser_id());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        response.sendRedirect(request.getContextPath() + "/order");
    }
}
