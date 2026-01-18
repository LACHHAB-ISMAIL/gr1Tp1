package com.library.model;

public class TestVerification {
    public static void main(String[] args) {
        System.out.println("=== TEST DE VERIFICATION ===");

        // Test 1: Créer un User
        User user = new User(1, "test@test.com", "password", "Admin");
        System.out.println("✓ User créé: " + user.getLogin());
        System.out.println("✓ Est Admin: " + user.isAdmin());

        // Test 2: Créer un Auteur
        Auteur auteur = new Auteur(1, "Hugo", "Victor", "Masculin");
        System.out.println("✓ Auteur créé: " + auteur.getNomComplet());

        // Test 3: Créer un Livre
        Livre livre = new Livre();
        livre.setIsbn(123456789L);
        livre.setTitre("Les Misérables");
        livre.setEditeur("DUNOD");
        System.out.println("✓ Livre créé: " + livre.getTitre());

        System.out.println("\n=== TOUS LES TESTS PASSES ! ===");
    }}