package com.library.servlet;

import com.library.dao.AuteurDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/auteurs/delete")
public class AuteurDeleteServlet extends HttpServlet {

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

        int matricule = Integer.parseInt(request.getParameter("matricule"));

        // Optionnel : vérifier s'il y a des livres liés avant suppression
        // if (auteurDAO.hasLivres(matricule)) { ... message d'erreur ... }

        auteurDAO.delete(matricule);

        response.sendRedirect(request.getContextPath() + "/auteurs");
    }
}