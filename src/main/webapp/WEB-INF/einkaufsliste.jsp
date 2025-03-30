<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.webanwendungenmitjavahausarbeit.model.Einkaufsliste" %>
<%@ page import="com.example.webanwendungenmitjavahausarbeit.model.EinkaufslisteItem" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html lang="de">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Einkaufsliste</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        /* Set the entire page background to grey */
        body {
            background-color: #2e2e2e; /* Dark Grey Background */
            color: #ffffff;
        }

        /* Center the container and make it black */
        .container {
            background-color: #111; /* Black Einkaufsliste Box */
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.3); /* Subtle shadow */
        }

        /* Style cards inside the Einkaufsliste */
        .card {
            background-color: #1a1a1a; /* Slightly lighter black for contrast */
            border: 1px solid #333;
            color: #ffffff;
        }

        /* Ensure titles and buttons match the dark theme */
        .card-title {
            color: #ffffff;
        }

        .btn-primary, .btn-success, .btn-danger, .btn-info {
            background-color: #444;
            border: 1px solid #666;
            color: #ffffff;
        }

        .btn-primary:hover, .btn-success:hover, .btn-danger:hover, .btn-info:hover {
            background-color: #666;
        }

        /* Style list items */
        .list-group-item {
            background-color: #222;
            color: #ffffff;
            border: 1px solid #444;
        }

        /* Error messages in dark mode */
        .alert-danger {
            background-color: #990000;
            color: #ffffff;
            border: 1px solid #cc0000;
        }

        /* Modal background styling */
        .modal-content {
            background-color: #1a1a1a;
            color: #ffffff;
        }

        /* Close button in dark mode */
        .btn-close {
            filter: invert(1);
        }

    </style>
</head>
<body class="bg-light">

<div class="container mt-5">
    <h2 class="mb-4">🛒 Einkaufsliste</h2>

    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
    <div class="alert alert-danger"><%= error %></div>
    <% } %>

    <!-- Form to create a new Einkaufsliste -->
    <div class="card mb-4">
        <div class="card-body">
            <h5 class="card-title">📋 Neue Einkaufsliste erstellen</h5>
            <form action="${pageContext.request.contextPath}/createEinkaufsliste" method="post">
                <div class="mb-3">
                    <label for="title" class="form-label">Titel:</label>
                    <input type="text" class="form-control" id="title" name="title" required>
                </div>

                <input type="hidden" name="date" value="<%= request.getParameter("date") != null ? request.getParameter("date") : LocalDate.now().toString() %>">
                <input type="hidden" name="userId" value="<%= session.getAttribute("userId") %>">

                <button type="submit" class="btn btn-primary">Liste erstellen</button>
            </form>
        </div>
    </div>

    <h3>📝 Deine Einkaufslisten</h3>

    <%
        // Get the lists from the request attribute
        List<Einkaufsliste> listen = (List<Einkaufsliste>) request.getAttribute("einkaufslisten");

        if (listen == null || listen.isEmpty()) {
    %>
    <p class="text-muted">Keine Einkaufslisten vorhanden.</p>
    <%
    } else {
        for (Einkaufsliste liste : listen) {
    %>
    <div class="card mb-3">
        <div class="card-body">
            <h5 class="card-title d-flex justify-content-between align-items-center">
                <span><%= liste.getTitle() %> (📅 <%= liste.getDatum() %>)</span>

                <!-- AI Recommendation Button (top right corner) -->
                <button type="button" class="btn btn-info btn-sm" data-bs-toggle="modal" data-bs-target="#aiModal<%= liste.getId() %>">
                    AI Vorschlag
                </button>
            </h5>

            <!-- Form for deleting the list -->
            <form action="${pageContext.request.contextPath}/einkaufsliste" method="post" class="d-inline">
                <input type="hidden" name="action" value="deleteList">
                <input type="hidden" name="listId" value="<%= liste.getId() %>">
                <input type="hidden" name="date" value="<%= request.getParameter("date") != null ? request.getParameter("date") : LocalDate.now().toString() %>">
                <input type="hidden" name="userId" value="<%= session.getAttribute("userId") %>">
                <button type="submit" class="btn btn-danger btn-sm">🗑 Löschen</button>
            </form>

            <!-- Form for adding a new item to the list -->
            <form action="${pageContext.request.contextPath}/einkaufsliste" method="post" class="mb-3">
                <input type="hidden" name="listId" value="<%= liste.getId() %>">
                <input type="hidden" name="userId" value="<%= session.getAttribute("userId") %>">
                <input type="hidden" name="date" value="<%= request.getParameter("date") != null ? request.getParameter("date") : LocalDate.now().toString() %>">
                <div class="input-group">
                    <input type="text" class="form-control" name="item" placeholder="Neuer Artikel..." required>
                    <button type="submit" class="btn btn-success">➕ Hinzufügen</button>
                </div>
            </form>

            <ul class="list-group">
                <%
                    List<EinkaufslisteItem> items = liste.getItems();
                    if (items == null || items.isEmpty()) {
                %>
                <li class="list-group-item text-muted">Keine Artikel vorhanden.</li>
                <%
                } else {
                    for (EinkaufslisteItem item : items) {
                %>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    <%= item.getItem() %>
                    <!-- Form for deleting an item -->
                    <form action="${pageContext.request.contextPath}/einkaufsliste" method="post" class="d-inline">
                        <input type="hidden" name="action" value="deleteItem">
                        <input type="hidden" name="userId" value="<%= session.getAttribute("userId") %>">
                        <input type="hidden" name="date" value="<%= request.getParameter("date") != null ? request.getParameter("date") : LocalDate.now().toString() %>">
                        <input type="hidden" name="itemId" value="<%= item.getId() %>">
                        <button type="submit" class="btn btn-danger btn-sm">🗑</button>
                    </form>
                </li>
                <%
                        }
                    }
                %>
            </ul>
        </div>
    </div>

    <!-- Modal for AI Vorschlag -->
    <div class="modal fade" id="aiModal<%= liste.getId() %>" tabindex="-1" aria-labelledby="aiModalLabel<%= liste.getId() %>" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="aiModalLabel<%= liste.getId() %>">AI Vorschlag hinzufügen</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Form to submit AI Vorschlag -->
                    <form action="${pageContext.request.contextPath}/einkaufsliste" method="post">
                        <input type="hidden" name="action" value="aiList">
                        <input type="hidden" name="listId" value="<%= liste.getId() %>">
                        <input type="hidden" name="userId" value="<%= session.getAttribute("userId") %>">
                        <input type="hidden" name="date" value="<%= request.getParameter("date") != null ? request.getParameter("date") : LocalDate.now().toString() %>">

                        <div class="mb-3">
                            <label for="gerichtInput<%= liste.getId() %>" class="form-label">Gericht</label>
                            <input type="text" class="form-control" id="gerichtInput<%= liste.getId() %>" placeholder="Gericht eingeben" name="gericht" required>
                        </div>
                        <div class="mb-3">
                            <label for="preisInput<%= liste.getId() %>" class="form-label">Preis</label>
                            <input type="number" class="form-control" id="preisInput<%= liste.getId() %>" placeholder="Preis eingeben" min="0" step="0.01" name="preis" required>
                        </div>

                        <button type="submit" class="btn btn-primary">Vorschlag hinzufügen</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <%
            }
        }
    %>

    <a href="${pageContext.request.contextPath}/calendar" class="btn btn-secondary mt-3">⬅ Zurück zum Kalender</a>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
