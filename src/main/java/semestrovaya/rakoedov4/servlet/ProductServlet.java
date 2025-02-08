package semestrovaya.rakoedov4.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import semestrovaya.rakoedov4.entity.Product;
import semestrovaya.rakoedov4.service.ProductService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    ProductService productService;

    @Override
    public void init() throws ServletException {
        this.productService = (ProductService) getServletContext().getAttribute("productService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productIdParam = request.getParameter("product_id");

        if (productIdParam == null || productIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is null");
            return;
        }
        try {
            int productId = Integer.parseInt(productIdParam);
            Product product = productService.findById(productId);
            if (product != null) {
                request.setAttribute("product", product);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/product.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID");
        }
    }

}
