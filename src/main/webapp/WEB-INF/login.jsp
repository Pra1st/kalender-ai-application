<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>

<%
    // Check if user is logged in
    String loggedInUser = (String) session.getAttribute("username");
    String errorMessage = (String) request.getAttribute("error"); // Get error message from request attribute
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            text-align: center;
        }
        .container {
            background-color: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            width: 300px;
        }
        h1 {
            margin-bottom: 20px;
            font-size: 24px;
        }
        .error-message {
            color: red;
            font-size: 14px;
            margin-bottom: 15px;
        }
        input {
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
        }
        button {
            width: 100%;
            padding: 12px;
            background-color: black;
            color: white;
            font-size: 16px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-bottom: 10px;
        }
        button:hover {
            background-color: #444;
        }
        .back-button {
            background-color: #ddd;
            color: black;
        }
        .back-button:hover {
            background-color: #bbb;
        }
        .footer {
            margin-top: 20px;
        }
        .footer a {
            text-decoration: none;
            color: black;
            font-size: 14px;
        }
        .footer a:hover {
            color: #444;
        }
    </style>
</head>
<body>

<% if (loggedInUser == null) { %>
<div class="container">
    <h1>Login</h1>

    <% if (errorMessage != null) { %>
    <p class="error-message"><%= errorMessage %></p>
    <% } %>

    <form method="POST" action="login">
        <input type="text" name="username" placeholder="Username" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Login</button>
    </form>

    <!-- Back to Calendar Button -->
    <a href="calendar">
        <button class="back-button">Back to Calendar</button>
    </a>

    <div class="footer">
        <p>Don't have an account? <a href="register">Register here</a></p>
    </div>
</div>
<% } else { %>
<h1>Welcome, <%= loggedInUser %>!</h1>
<a href="logout" class="login-btn">Logout</a>
<% } %>

</body>
</html>
