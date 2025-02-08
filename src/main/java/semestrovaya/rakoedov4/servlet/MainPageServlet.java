package semestrovaya.rakoedov4.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import semestrovaya.rakoedov4.entity.Client;
import semestrovaya.rakoedov4.entity.Product;
import semestrovaya.rakoedov4.service.CartService;
import semestrovaya.rakoedov4.service.ProductService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/mainpage")
public class MainPageServlet extends HttpServlet {
    ProductService productService;
    CartService cartService;

    @Override
    public void init() throws ServletException {
        productService = (ProductService) getServletContext().getAttribute("productService");
        cartService = (CartService) getServletContext().getAttribute("cartService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = new ArrayList<>();
        try {
            products = productService.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("products", products);
        request.getRequestDispatcher("WEB-INF/views/mainpage.jsp").forward(request, response);
    }

}
