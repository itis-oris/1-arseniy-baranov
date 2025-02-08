package semestrovaya.rakoedov4.filter;

import semestrovaya.rakoedov4.entity.Client;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({"/cart", "/order", "/clientPage"})
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);

        Client authenticatedClient = (session != null) ? (Client) session.getAttribute("client") : null;

        if (authenticatedClient == null) {
            System.out.println("Фильтр не пускает");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
