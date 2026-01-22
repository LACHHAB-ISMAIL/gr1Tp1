# Gestion des livres (Library Management System)

Application web de gestion de bibliothèque développée en **Java (Jakarta EE)** respectant le modèle architectural **MVC (Modèle-Vue-Contrôleur)**.


- **Authentification** : Connexion sécurisée pour les utilisateurs/administrateurs.
- **Gestion des Auteurs** : Ajouter, modifier, supprimer et lister les auteurs.
- **Gestion des Livres** : Ajouter, modifier, supprimer et rechercher des livres.
- **Recherche avancée** : Rechercher des livres par titre, auteur ou date.
- **Internationalisation** : Support de trois langues (français, anglais et arabe).
- **Gestion des rôles** : Deux rôles utilisateurs (Admin et Visiteur) avec des droits différents.


- **Langage** : Java 17
- **Framework Web** : Jakarta EE 10 (Servlets, JSP, JSTL)
- **Base de Données** : MySQL
- **Build Tool** : Maven
- **Serveur d'Application** : Tomcat / Jetty (via Maven Plugin ou IDE)


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


Le script de création de la base de données se trouve dans : [`database/schema.sql`](database/schema.sql).

### Schéma Relationnel
- **AUTEUR** : `matricule` (PK), `nom`, `prenom`, `genre`.
- **LIVRE** : `ISBN` (PK), `titre`, `description`, `date_edition`, `editeur`, `matricule` (FK vers AUTEUR).
- **user** : `id` (PK), `login`, `password`, `role`.

### Contraintes de Validation
- **Genre de l'auteur** : Doit être "Masculin" ou "Féminin"
- **Éditeur du livre** : Doit être parmi {ENI, DUNOD, FIRST}
- **Rôles utilisateur** : Deux rôles possibles : ADMIN ou VISITEUR

### Configuration
La connexion est configurée dans `com.library.util.DatabaseConnection` :
- **URL** : `jdbc:mysql://localhost:3306/gr1Tp1Db`
- **User** : `root`
- **Password** : *(vide)*

---

## Internationalisation
L'application supporte trois langues : français, anglais et arabe.

### Implémentation
- Utilisation de fichiers de propriétés pour les traductions :
  - `messages_fr.properties` : Traductions en français
  - `messages_en.properties` : Traductions en anglais
  - `messages_ar.properties` : Traductions en arabe
- Utilisation de la balise `<fmt:setBundle basename="messages" />` de JSTL dans toutes les pages JSP
- Utilisation de la balise `<fmt:message key="..." />` pour afficher les textes traduits
- Filter `LanguageFilter` pour gérer le changement de langue
- Stockage de la langue choisie en session via `session.setAttribute("lang", lang)`
- Configuration de la locale JSTL via `Config.set(session, Config.FMT_LOCALE, locale)`

### Gestion de la langue
- Sélecteur de langue dans l'interface utilisateur (FR, EN, AR)
- La langue par défaut est le français
- La langue est conservée en session utilisateur
- Pour l'arabe, l'interface s'adapte automatiquement avec :
  - Direction RTL (Right-to-Left) via l'attribut `dir="rtl"`
  - Utilisation de Bootstrap RTL pour le style adapté

### Exemple d'utilisation dans JSP
```jsp
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setBundle basename="messages" />
<html lang="${sessionScope.lang}" dir="${sessionScope.lang == 'ar' ? 'rtl' : 'ltr'}">
<!-- Utilisation des messages -->
<fmt:message key="app.title" />
```

---

## Gestion des Rôles et Droits d'Accès

### Rôles Utilisateurs
L'application implémente deux rôles :
- **ADMIN** : Accès complet à toutes les fonctionnalités (CRUD sur livres et auteurs)
- **VISITEUR** : Accès en lecture seule à la liste des livres et recherche

### Contrôle d'Accès
- Filter frontal pour gérer l'authentification
- Vérification des rôles avant l'accès aux ressources
- Redirection automatique selon les droits de l'utilisateur
- Protection des pages d'administration

---


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
