# Gestion de Bibliothèque (Library Management System)

Application web de gestion de bibliothèque développée en **Java (Jakarta EE)** respectant le modèle architectural **MVC (Modèle-Vue-Contrôleur)**.

## Fonctionnalités

- **Authentification** : Connexion sécurisée pour les utilisateurs/administrateurs.
- **Gestion des Auteurs** : Ajouter, modifier, supprimer et lister les auteurs.
- **Gestion des Livres** : Ajouter, modifier, supprimer et rechercher des livres.
- **Recherche avancée** : Rechercher des livres par titre, auteur ou date.

## Technologies Utilisées

- **Langage** : Java 17
- **Framework Web** : Jakarta EE 10 (Servlets, JSP, JSTL)
- **Base de Données** : MySQL
- **Build Tool** : Maven
- **Serveur d'Application** : Tomcat / Jetty (via Maven Plugin ou IDE)

## Structure du Projet (MVC)

Le projet suit strictement l'architecture MVC pour séparer les préoccupations :

### 1. Modèle (Model)
Situé dans `com.library.model`.
Contient les classes Java représentant les données de l'application :
- `Livre.java` : Représente un livre.
- `Auteur.java` : Représente un auteur.
- `User.java` : Représente u un utilisateur du système.

### 2. Vue (View)
Situé dans `src/main/webapp` et `WEB-INF/views`.
Contient les fichiers JSP responsables de l'interface utilisateur :
- `index.jsp` / `login.jsp` : Pages d'accueil et de connexion.
- `WEB-INF/views/` : Vues pour lister et modifier les livres et auteurs.

### 3. Contrôleur (Controller)
Situé dans `com.library.servlet`.
Contient les Servlets qui reçoivent les requêtes HTTP, interagissent avec le modèle et redirigent vers la vue appropriée :
- `LivreServlet`, `AuteurServlet` : Gèrent les opérations CRUD.
- `LoginServlet` : Gère l'authentification.
- `SearchServlet` : Gère la recherche.

### 4. Accès aux Données (DAO)
Situé dans `com.library.dao`.
Gère toutes les interactions avec la base de données (SQL) :
- `LivreDAO`, `AuteurDAO`, `UserDAO`.
- `DatabaseConnection` (`com.library.util`) : Gère la connexion JDBC à MySQL.

---

## Base de Données

Le script de création de la base de données se trouve dans : [`database/schema.sql`](database/schema.sql).

### Schéma Relationnel
- **AUTEUR** : `matricule` (PK), `nom`, `prenom`, `genre`.
- **LIVRE** : `ISBN` (PK), `titre`, `description`, `date_edition`, `editeur`, `matricule` (FK vers AUTEUR).
- **user** : `id` (PK), `login`, `password`, `role`.

### Configuration
La connexion est configurée dans `com.library.util.DatabaseConnection` :
- **URL** : `jdbc:mysql://localhost:3306/gr1Tp1Db`
- **User** : `root`
- **Password** : *(vide)*

---

## Installation et Exécution

1. **Prérequis** :
   - JDK 17+
   - Maven
   - MySQL Server

2. **Base de Données** :
   - Exécutez le script SQL `database/schema.sql` dans votre client MySQL pour créer la base et les tables.

3. **Compilation et Lancement** :
   ```bash
   mvn clean install
   ```
   Déployez le fichier `.war` généré (dans `target/`) sur votre serveur Tomcat, ou lancez via votre IDE (IntelliJ IDEA / Eclipse).

4. **Accès** :
   - URL : `http://localhost:8080/gr1Tp1`
   - Login par défaut : `admin` / `admin`
