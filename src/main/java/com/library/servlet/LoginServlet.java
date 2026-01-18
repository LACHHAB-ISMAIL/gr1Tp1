package com.library.servlet;

import com.library.dao.UserDAO;
import com.library.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO UserDAO;

    @Override
    public void init() throws ServletException {
        UserDAO = new UserDAO(); // ou via @Inject si CDI
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = UserDAO.findByLogin(login, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());

            if ("Admin".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/livres");
            } else {
                response.sendRedirect(request.getContextPath() + "/livres?viewOnly=true");
            }
        } else {
            request.setAttribute("error", "Identifiants incorrects");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}