<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Logged Out</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f4;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
      text-align: center;
      margin: 0;
      padding: 0;
    }
    .container {
      background-color: white;
      padding: 40px;
      border-radius: 10px;
      box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
      width: 350px;
    }
    h1 {
      margin-bottom: 20px;
      font-size: 24px;
    }
    .message {
      margin-bottom: 20px;
      font-size: 18px;
    }
    .button-container {
      display: flex;
      justify-content: space-between;
      gap: 10px;
    }
    .btn {
      flex: 1;
      padding: 12px;
      border: none;
      border-radius: 5px;
      font-size: 16px;
      cursor: pointer;
      text-decoration: none;
      display: inline-block;
      text-align: center;
    }
    .login-btn {
      background-color: black;
      color: white;
    }
    .login-btn:hover {
      background-color: #444;
    }
    .calendar-btn {
      background-color: #ddd;
      color: black;
    }
    .calendar-btn:hover {
      background-color: #bbb;
    }
  </style>
</head>
<body>

<div class="container">
  <h1>You have been logged out</h1>
  <p class="message">You can now log in again or return to the calendar.</p>

  <div class="button-container">
    <a href="login" class="btn login-btn">Log In</a>
    <a href="calendar" class="btn calendar-btn">Back to Calendar</a>
  </div>
</div>

</body>
</html>
