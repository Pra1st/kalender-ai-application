<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f4f4f4;
            margin: 0;
            font-family: Arial, sans-serif;
        }
        .container {
            text-align: center;
        }
        .btn {
            display: inline-block;
            padding: 20px 40px;
            font-size: 24px;
            font-weight: bold;
            text-transform: uppercase;
            background-color: black;
            color: white;
            border: none;
            cursor: pointer;
            text-decoration: none;
            border-radius: 10px;
            transition: background 0.3s ease-in-out;
        }
        .btn:hover {
            background-color: #444;
        }
    </style>
</head>
<body>
<div class="container">
    <a href="calendar" class="btn">Calendar</a>
</div>
</body>
</html>
