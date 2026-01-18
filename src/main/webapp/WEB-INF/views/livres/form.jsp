<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>${livre != null ? 'Modifier' : 'Ajouter'} un Livre</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-5">
    <h2>${livre != null ? 'Modifier le livre' : 'Nouveau livre'}</h2>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/livres/${livre != null ? 'edit' : 'add'}" method="post">
        <input type="hidden" name="action" value="${livre != null ? 'update' : 'create'}">

        <div class="mb-3">
            <label for="isbn" class="form-label">ISBN</label>
            <input type="number" class="form-control" id="isbn" name="isbn"
                   value="${livre.isbn}" ${livre != null ? 'readonly' : 'required'}>
        </div>

        <div class="mb-3">
            <label for="titre" class="form-label">Titre</label>
            <input type="text" class="form-control" id="titre" name="titre" value="${livre.titre}" required>
        </div>

        <div class="mb-3">
            <label for="description" class="form-label">Description</label>
            <textarea class="form-control" id="description" name="description" rows="4">${livre.description}</textarea>
        </div>

        <div class="mb-3">
            <label for="dateEdition" class="form-label">Date d'édition</label>
            <input type="date" class="form-control" id="dateEdition" name="dateEdition"
                   value="${livre.dateEdition}" required>
        </div>

        <div class="mb-3">
            <label for="editeur" class="form-label">Éditeur</label>
            <select class="form-select" id="editeur" name="editeur" required>
                <c:forEach var="e" items="${editeurs}">
                    <option value="${e}" ${e == livre.editeur ? 'selected' : ''}>${e}</option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <label for="matriculeAuteur" class="form-label">Auteur</label>
            <select class="form-select" id="matriculeAuteur" name="matriculeAuteur" required>
                <c:forEach var="a" items="${auteurs}">
                    <option value="${a.matricule}" ${a.matricule == livre.matriculeAuteur ? 'selected' : ''}>
                            ${a.nom} ${a.prenom}
                    </option>
                </c:forEach>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Enregistrer</button>
        <a href="${pageContext.request.contextPath}/livres" class="btn btn-secondary">Annuler</a>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>