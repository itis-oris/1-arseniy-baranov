package semestrovaya.rakoedov4.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import semestrovaya.rakoedov4.entity.Client;
import semestrovaya.rakoedov4.exception.EmailAlreadyExistException;
import semestrovaya.rakoedov4.service.ClientService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private ClientService clientService;

    @Override
    public void init(){
        this.clientService = (ClientService) getServletContext().getAttribute("clientService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("WEB-INF/views/registration.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean hasErrors = false;

        if (firstName == null || firstName.isEmpty()) {
            request.setAttribute("errorFirstName", "Имя не может быть пустым.");
            hasErrors = true;
        }

        if (lastName == null || lastName.isEmpty()) {
            request.setAttribute("errorLastName", "Фамилия не может быть пустой.");
        }

        if (email == null || email.isEmpty()) {
            request.setAttribute("errorEmail", "Email не может быть пустым.");
            hasErrors = true;
        }

        if (password == null || password.isEmpty()) {
            request.setAttribute("errorPassword", "Пароль не может быть пустым.");
            hasErrors = true;
        }
        if (hasErrors) {
            request.getRequestDispatcher("WEB-INF/views/registration.jsp").forward(request, response);
            return;
        }
        try {
            Client client = new Client(firstName, lastName, phone, email, password);
            clientService.register(client);
            client = clientService.findByEmail(email);
            HttpSession session = request.getSession();
            session.setAttribute("client", client);
            response.sendRedirect("clientPage");

        } catch (EmailAlreadyExistException e) {

            request.setAttribute("errorEmail", e.getMessage());
            request.getRequestDispatcher("WEB-INF/views/registration.jsp").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
