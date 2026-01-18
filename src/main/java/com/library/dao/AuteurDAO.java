package com.library.dao;

import com.library.model.Auteur;
import com.library.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuteurDAO {

    public List<Auteur> findAll() {
        List<Auteur> auteurs = new ArrayList<>();
        String sql = "SELECT * FROM AUTEUR ORDER BY nom, prenom";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                auteurs.add(extractAuteur(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de tous les auteurs", e);
        }
        return auteurs;
    }

    public Auteur findByMatricule(int matricule) {
        String sql = "SELECT * FROM AUTEUR WHERE matricule = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricule);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractAuteur(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de l'auteur matricule " + matricule, e);
        }
        return null;
    }

    public List<Auteur> search(String nom, String prenom, String genre) {
        List<Auteur> auteurs = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM AUTEUR WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (nom != null && !nom.trim().isEmpty()) {
            sql.append(" AND nom LIKE ?");
            params.add("%" + nom.trim() + "%");
        }
        if (prenom != null && !prenom.trim().isEmpty()) {
            sql.append(" AND prenom LIKE ?");
            params.add("%" + prenom.trim() + "%");
        }
        if (genre != null && !genre.trim().isEmpty()) {
            sql.append(" AND genre = ?");
            params.add(genre.trim());
        }
        sql.append(" ORDER BY nom, prenom");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    auteurs.add(extractAuteur(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche d'auteurs", e);
        }
        return auteurs;
    }

    public boolean create(Auteur auteur) {
        String sql = "INSERT INTO AUTEUR (nom, prenom, genre) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, auteur.getNom());
            stmt.setString(2, auteur.getPrenom());
            stmt.setString(3, auteur.getGenre());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                return false;
            }

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    auteur.setMatricule(keys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            // Si duplicate key (matricule existe déjà) → tu peux catcher SQLIntegrityConstraintViolationException
            throw new RuntimeException("Erreur lors de la création de l'auteur", e);
        }
    }

    public boolean update(Auteur auteur) {
        String sql = "UPDATE AUTEUR SET nom = ?, prenom = ?, genre = ? WHERE matricule = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, auteur.getNom());
            stmt.setString(2, auteur.getPrenom());
            stmt.setString(3, auteur.getGenre());
            stmt.setInt(4, auteur.getMatricule());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'auteur " + auteur.getMatricule(), e);
        }
    }

    public boolean delete(int matricule) {
        String sql = "DELETE FROM AUTEUR WHERE matricule = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricule);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Attention : si l'auteur a des livres → violation de clé étrangère
            throw new RuntimeException("Erreur lors de la suppression de l'auteur " + matricule, e);
        }
    }

    // Méthode utilitaire privée
    private Auteur extractAuteur(ResultSet rs) throws SQLException {
        return new Auteur(
                rs.getInt("matricule"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("genre")
        );
    }
}