package semestrovaya.rakoedov4.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import semestrovaya.rakoedov4.entity.Client;
import semestrovaya.rakoedov4.service.ClientService;

import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    public ClientService clientService;

    @Override
    public void init(){
        this.clientService = (ClientService) getServletContext().getAttribute("clientService");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.setAttribute("errorEmpty", "Пожалуйста, заполните все поля.");
            request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        Client client;
        try {
            client = clientService.login(email, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (client != null) {
            HttpSession session = request.getSession();
            session.setAttribute("client", client);
            response.sendRedirect("clientPage");
        } else {
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.setAttribute("errorInvalid", "Неверный пароль или e-mail.");
            request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
    }
}
