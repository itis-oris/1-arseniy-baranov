package semestrovaya.rakoedov4.additions;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//Отображение байтовых изображений
@WebServlet("/image")
public class ImageServlet extends HttpServlet {

    private static final String URL = "jdbc:postgresql://localhost:5432/rakoedov_db"; // URL базы данных
    private static final String USER = "postgres";
    private static final String PASSWORD = "qwerty007";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        // ex: /image?product_id=1)
        String productId = request.getParameter("product_id");
        if (productId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required");
            return;
        }


        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT image FROM product WHERE product_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, Integer.parseInt(productId));

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    InputStream is = rs.getBinaryStream("image");
                    if (is == null) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
                        return;
                    }

                    response.setContentType("image/jpeg");
                    OutputStream os = response.getOutputStream();
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }
                    os.close();
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                }
            }
        } catch (Exception e) {
            throw new ServletException("Unable to retrieve image", e);
        }
    }
}
