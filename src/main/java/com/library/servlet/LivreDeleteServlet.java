package com.library.servlet;

import com.library.dao.LivreDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/livres/delete")
public class LivreDeleteServlet extends HttpServlet {

    private LivreDAO livreDAO;

    @Override
    public void init() throws ServletException {
        livreDAO = new LivreDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!"Admin".equals(request.getSession().getAttribute("role"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Long isbn = Long.parseLong(request.getParameter("isbn"));
        livreDAO.delete(isbn);

        response.sendRedirect(request.getContextPath() + "/livres");
    }
}