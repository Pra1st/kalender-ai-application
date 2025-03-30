<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.webanwendungenmitjavahausarbeit.service.UserService" %>

<%
    UserService userService = new UserService();

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            out.println("<p style='color:red;'>Passwords do not match!</p>");
        } else {
            boolean success = userService.register(username, email, password);
            if (success) {
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);  // Redirect to login after successful registration
                return;
            } else {
                out.println("<p style='color:red;'>Error: User already exists or database error.</p>");
            }
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
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
        }
        button:hover {
            background-color: #444;
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

<div class="container">
    <h1>Register</h1>
    <form method="POST" action="register">
        <input type="text" name="username" placeholder="Username" required>
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <input type="password" name="confirmPassword" placeholder="Confirm Password" required>
        <button type="submit">Register</button>
    </form>

    <div class="footer">
        <p>Already have an account? <a href="login">Login here</a></p>
    </div>
</div>

</body>
</html>
