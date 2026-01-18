package com.library.servlet;

import com.library.dao.AuteurDAO;
import com.library.model.Auteur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet({"/auteurs/add", "/auteurs/edit"})
public class AuteurFormServlet extends HttpServlet {

    private AuteurDAO auteurDAO;

    @Override
    public void init() throws ServletException {
        auteurDAO = new AuteurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!"Admin".equals(request.getSession().getAttribute("role"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès réservé aux administrateurs");
            return;
        }

        String matriculeStr = request.getParameter("matricule");
        if (matriculeStr != null && !matriculeStr.isBlank()) {
            int matricule = Integer.parseInt(matriculeStr);
            Auteur auteur = auteurDAO.findByMatricule(matricule);
            request.setAttribute("auteur", auteur);
        }

        request.setAttribute("genres", new String[]{"Masculin", "Féminin"});

        request.getRequestDispatcher("/WEB-INF/views/auteurs/form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!"Admin".equals(request.getSession().getAttribute("role"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès réservé aux administrateurs");
            return;
        }

        int matricule = Integer.parseInt(request.getParameter("matricule"));
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String genre = request.getParameter("genre");

        // Validation minimale du genre
        if (!"Masculin".equals(genre) && !"Féminin".equals(genre)) {
            request.setAttribute("error", "Le genre doit être Masculin ou Féminin");
            request.getRequestDispatcher("/WEB-INF/views/auteurs/form.jsp").forward(request, response);
            return;
        }

        Auteur auteur = new Auteur(matricule, nom, prenom, genre);

        String action = request.getParameter("action");  // "create" ou "update"
        if ("update".equals(action)) {
            auteurDAO.update(auteur);
        } else {
            // Vérifier unicité matricule avant création (selon ton DAO)
            if (auteurDAO.findByMatricule(matricule) != null) {
                request.setAttribute("error", "Ce matricule existe déjà");
                request.getRequestDispatcher("/WEB-INF/views/auteurs/form.jsp").forward(request, response);
                return;
            }
            auteurDAO.create(auteur);
        }

        response.sendRedirect(request.getContextPath() + "/auteurs");
    }
}