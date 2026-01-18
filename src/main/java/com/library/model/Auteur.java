package com.library.model;

import java.io.Serializable;

public class Auteur implements Serializable {
    private static final long serialVersionUID = 1L;

    private int matricule;
    private String nom;
    private String prenom;
    private String genre;

    public Auteur() {}

    public Auteur(int matricule, String nom, String prenom, String genre) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.genre = genre;
    }

    // Getters et Setters
    public int getMatricule() { return matricule; }
    public void setMatricule(int matricule) { this.matricule = matricule; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getNomComplet() {
        return prenom + " " + nom;
    }

    @Override
    public String toString() {
        return "Auteur{matricule=" + matricule + ", nom='" + nom +
                "', prenom='" + prenom + "', genre='" + genre + "'}";
    }
}