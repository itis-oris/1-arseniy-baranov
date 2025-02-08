package semestrovaya.rakoedov4.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import semestrovaya.rakoedov4.entity.Cart;
import semestrovaya.rakoedov4.entity.Client;
import semestrovaya.rakoedov4.entity.Product;
import semestrovaya.rakoedov4.service.CartService;
import semestrovaya.rakoedov4.service.ProductService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    CartService cartService;
    ProductService productService;

    @Override
    public void init() throws ServletException {
        this.cartService = (CartService) getServletContext().getAttribute("cartService");
        this.productService = (ProductService) getServletContext().getAttribute("productService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");
        if (client == null) {
            request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
        }
        try {
            int clientId = client.getUser_id();
            List<Cart> cartItems = cartService.findByClientId(clientId);
            List<Product> products = productService.findAll();
            request.setAttribute("cartItems", cartItems);
            request.setAttribute("products", products);
            request.getRequestDispatcher("WEB-INF/views/cart.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");
        if (client == null) {
            request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
        }

        int productId = Integer.parseInt(request.getParameter("productId"));
        String quantityStr = request.getParameter("quantity");

        int quantity = Integer.parseInt(quantityStr);

        try {
            Cart isCartExist = cartService.findByClientIdAndProductId(client.getUser_id(), productId);

            if (isCartExist == null) {

                Cart newCart = new Cart(client.getUser_id(), productId, quantity);
                cartService.create(newCart);
            } else {

                cartService.addToCart(client.getUser_id(), productId, quantity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String action = request.getParameter("action");

        if ("remove".equals(action)) {
            cartService.removeFromCart(client.getUser_id(), productId);
        }
        response.sendRedirect(request.getContextPath() + "/cart");
    }


}
