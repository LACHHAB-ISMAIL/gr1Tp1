<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Liste des Livres</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/livres">Bibliothèque</a>
    <div class="navbar-nav ms-auto">
      <c:if test="${not empty sessionScope.user}">
        <span class="nav-link text-light">Bonjour, ${sessionScope.user.login} (${sessionScope.role})</span>
        <a class="nav-link" href="${pageContext.request.contextPath}/logout">Déconnexion</a>
      </c:if>
    </div>
  </div>
</nav>

<div class="container mt-4">

  <c:if test="${not empty error}">
    <div class="alert alert-danger alert-dismissible fade show">
        ${error}
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>

  <h2 class="mb-4">Liste des Livres
    <c:if test="${isAdmin && !viewOnly}">
      <a href="${pageContext.request.contextPath}/livres/add" class="btn btn-success btn-sm float-end">+ Nouveau livre</a>
    </c:if>
  </h2>

  <table class="table table-striped table-hover">
    <thead class="table-dark">
    <tr>
      <th>ISBN</th>
      <th>Titre</th>
      <th>Auteur</th>
      <th>Éditeur</th>
      <th>Date d'édition</th>
      <c:if test="${isAdmin && !viewOnly}"><th>Actions</th></c:if>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="livre" items="${livres}">
      <tr>
        <td>${livre.isbn}</td>
        <td>${livre.titre}</td>
        <td>${livre.matriculeAuteur} <!-- À améliorer : afficher nom/prénom via jointure ou attribut --></td>
        <td>${livre.editeur}</td>
        <td>${livre.dateEditionFormatted}</td>
        <c:if test="${isAdmin && !viewOnly}">
          <td>
            <a href="${pageContext.request.contextPath}/livres/edit?isbn=${livre.isbn}"
               class="btn btn-sm btn-warning">Modifier</a>
            <a href="${pageContext.request.contextPath}/livres/delete?isbn=${livre.isbn}"
               class="btn btn-sm btn-danger"
               onclick="return confirm('Supprimer ce livre ?');">Supprimer</a>
          </td>
        </c:if>
      </tr>
    </c:forEach>
    </tbody>
  </table>

  <c:if test="${empty livres}">
    <p class="text-muted">Aucun livre trouvé.</p>
  </c:if>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>