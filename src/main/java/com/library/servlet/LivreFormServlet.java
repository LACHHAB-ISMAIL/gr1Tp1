package com.library.servlet;

import com.library.dao.LivreDAO;
import com.library.dao.AuteurDAO;
import com.library.model.Livre;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet({"/livres/add", "/livres/edit"})
public class LivreFormServlet extends HttpServlet {

    private LivreDAO livreDAO;
    private AuteurDAO auteurDAO;

    @Override
    public void init() throws ServletException {
        livreDAO = new LivreDAO();
        auteurDAO = new AuteurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!"Admin".equals(request.getSession().getAttribute("role"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String isbn = request.getParameter("isbn");
        if (isbn != null && !isbn.isBlank()) {
            Livre livre = livreDAO.findByIsbn(Long.parseLong(isbn));
            request.setAttribute("livre", livre);
        }

        request.setAttribute("auteurs", auteurDAO.findAll());
        request.setAttribute("editeurs", new String[]{"ENI", "DUNOD", "FIRST"});

        request.getRequestDispatcher("/WEB-INF/views/livres/form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!"Admin".equals(request.getSession().getAttribute("role"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Long isbn = Long.parseLong(request.getParameter("isbn"));
        String titre = request.getParameter("titre");
        String description = request.getParameter("description");
        LocalDate dateEdition = LocalDate.parse(request.getParameter("dateEdition"));
        String editeur = request.getParameter("editeur");
        int matriculeAuteur = Integer.parseInt(request.getParameter("matriculeAuteur"));

        Livre livre = new Livre(isbn, titre, description, dateEdition, editeur, matriculeAuteur);

        String action = request.getParameter("action");
        if ("update".equals(action)) {
            livreDAO.update(livre);
        } else {
            livreDAO.create(livre);
        }

        response.sendRedirect(request.getContextPath() + "/livres");
    }
}