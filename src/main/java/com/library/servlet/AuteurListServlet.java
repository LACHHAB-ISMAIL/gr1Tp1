package com.library.servlet;

import com.library.dao.AuteurDAO;
import com.library.model.Auteur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/auteurs")
public class AuteurListServlet extends HttpServlet {

    private AuteurDAO auteurDAO;

    @Override
    public void init() throws ServletException {
        auteurDAO = new AuteurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchNom = request.getParameter("nom");
        String searchPrenom = request.getParameter("prenom");
        String searchGenre = request.getParameter("genre");

        List<Auteur> auteurs;

        if (searchNom != null || searchPrenom != null || searchGenre != null) {
            auteurs = auteurDAO.search(searchNom, searchPrenom, searchGenre);
        } else {
            auteurs = auteurDAO.findAll();
        }

        request.setAttribute("auteurs", auteurs);
        request.setAttribute("isAdmin", "Admin".equals(request.getSession().getAttribute("role")));
        request.setAttribute("viewOnly", "true".equals(request.getParameter("viewOnly")));

        request.getRequestDispatcher("/WEB-INF/views/auteurs/list.jsp").forward(request, response);
    }
}