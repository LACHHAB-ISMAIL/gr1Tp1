package com.library.servlet;

import com.library.dao.LivreDAO;
import com.library.model.Livre;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/livres")
public class LivreListServlet extends HttpServlet {

    private LivreDAO livreDAO;

    @Override
    public void init() throws ServletException {
        livreDAO = new LivreDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchTitre = request.getParameter("titre");
        String searchAuteur = request.getParameter("auteur");
        String searchDate = request.getParameter("dateEdition");

        List<Livre> livres;

        if (searchTitre != null || searchAuteur != null || searchDate != null) {
            livres = livreDAO.search(searchTitre, searchAuteur, searchDate);
        } else {
            livres = livreDAO.findAll();
        }

        request.setAttribute("livres", livres);
        request.setAttribute("isAdmin", "Admin".equals(request.getSession().getAttribute("role")));
        request.setAttribute("viewOnly", "true".equals(request.getParameter("viewOnly")));

        request.getRequestDispatcher("/WEB-INF/views/livres/list.jsp").forward(request, response);
    }
}