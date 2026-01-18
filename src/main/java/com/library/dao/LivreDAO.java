package com.library.dao;

import com.library.model.Auteur;
import com.library.model.Livre;
import com.library.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAO {

    public List<Livre> findAll() {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT l.*, a.nom, a.prenom, a.genre " +
                "FROM LIVRE l JOIN AUTEUR a ON l.matricule = a.matricule " +
                "ORDER BY l.titre";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                livres.add(extractLivre(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }

    public Livre findByIsbn(long isbn) {
        String sql = "SELECT l.*, a.nom, a.prenom, a.genre " +
                "FROM LIVRE l JOIN AUTEUR a ON l.matricule = a.matricule " +
                "WHERE l.ISBN = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, isbn);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractLivre(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Livre> searchBycritere(String critere, String valeur) {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT l.*, a.nom, a.prenom, a.genre " +
                "FROM LIVRE l JOIN AUTEUR a ON l.matricule = a.matricule WHERE ";

        switch (critere) {
            case "titre":
                sql += "l.titre LIKE ?";
                break;
            case "auteur":
                sql += "(a.nom LIKE ? OR a.prenom LIKE ?)";
                break;
            case "date":
                sql += "l.date_edition = ?";
                break;
            default:
                return findAll();
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if ("auteur".equals(critere)) {
                stmt.setString(1, "%" + valeur + "%");
                stmt.setString(2, "%" + valeur + "%");
            } else if ("date".equals(critere)) {
                stmt.setDate(1, Date.valueOf(valeur));
            } else {
                stmt.setString(1, "%" + valeur + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    livres.add(extractLivre(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }

    public List<Livre> search(String searchTitre, String searchAuteur, String searchDate) {
        List<Livre> livres = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT l.*, a.nom, a.prenom, a.genre " +
                "FROM LIVRE l JOIN AUTEUR a ON l.matricule = a.matricule WHERE 1=1");

        List<Object> parameters = new ArrayList<>();

        // Ajouter le critère de recherche par titre
        if (searchTitre != null && !searchTitre.isEmpty()) {
            sql.append(" AND l.titre LIKE ?");
            parameters.add("%" + searchTitre + "%");
        }

        // Ajouter le critère de recherche par auteur
        if (searchAuteur != null && !searchAuteur.isEmpty()) {
            sql.append(" AND (a.nom LIKE ? OR a.prenom LIKE ?)");
            parameters.add("%" + searchAuteur + "%");
            parameters.add("%" + searchAuteur + "%");
        }

        // Ajouter le critère de recherche par date
        if (searchDate != null && !searchDate.isEmpty()) {
            sql.append(" AND l.date_edition = ?");
            parameters.add(Date.valueOf(searchDate));
        }

        // Exécuter la requête SQL avec les paramètres
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            // Paramétrer la requête avec les valeurs correspondantes
            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    livres.add(extractLivre(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livres;
    }

    public boolean create(Livre livre) {
        String sql = "INSERT INTO LIVRE (ISBN, titre, description, date_edition, editeur, matricule) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, livre.getIsbn());
            stmt.setString(2, livre.getTitre());
            stmt.setString(3, livre.getDescription());
            stmt.setDate(4, Date.valueOf(livre.getDateEdition()));
            stmt.setString(5, livre.getEditeur());
            stmt.setInt(6, livre.getMatriculeAuteur());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Livre livre) {
        String sql = "UPDATE LIVRE SET titre = ?, description = ?, date_edition = ?, " +
                "editeur = ?, matricule = ? WHERE ISBN = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getDescription());
            stmt.setDate(3, Date.valueOf(livre.getDateEdition()));
            stmt.setString(4, livre.getEditeur());
            stmt.setInt(5, livre.getMatriculeAuteur());
            stmt.setLong(6, livre.getIsbn());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(long isbn) {
        String sql = "DELETE FROM LIVRE WHERE ISBN = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, isbn);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Livre extractLivre(ResultSet rs) throws SQLException {
        Livre livre = new Livre(
                rs.getLong("ISBN"),
                rs.getString("titre"),
                rs.getString("description"),
                rs.getDate("date_edition").toLocalDate(),
                rs.getString("editeur"),
                rs.getInt("matricule")
        );

        Auteur auteur = new Auteur(
                rs.getInt("matricule"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("genre")
        );
        livre.setAuteur(auteur);

        return livre;
    }
}