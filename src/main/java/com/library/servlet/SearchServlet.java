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

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    private LivreDAO livreDAO;

    @Override
    public void init() throws ServletException {
        livreDAO = new LivreDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String critere = request.getParameter("critere");
        String valeur = request.getParameter("valeur");

        List<Livre> resultats = livreDAO.searchBycritere(critere, valeur);

        request.setAttribute("livres", resultats);
        request.setAttribute("isSearch", true);

        request.getRequestDispatcher("/WEB-INF/views/livres/list.jsp").forward(request, response);
    }
}