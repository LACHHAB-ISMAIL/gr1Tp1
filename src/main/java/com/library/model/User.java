package com.library.model;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String login;
    private String password;
    private String role;

    public User() {}

    public User(int id, String login, String password, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isAdmin() {
        return "Admin".equals(this.role);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", login='" + login + "', role='" + role + "'}";
    }
}