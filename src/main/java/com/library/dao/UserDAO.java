package com.library.dao;

import com.library.model.User;
import com.library.util.DatabaseConnection;
import java.sql.*;

public class UserDAO {

    public User authenticate(String login, String password) {
        String sql = "SELECT * FROM user WHERE login = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findByLogin(String login, String password) {
        String sql = "SELECT * FROM user WHERE login = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}