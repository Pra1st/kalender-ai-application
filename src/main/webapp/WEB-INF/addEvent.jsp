<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
    String eventDateStr = request.getParameter("date");
    LocalDate eventDate = LocalDate.parse(eventDateStr);
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Event</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background: #000;
            color: #fff;
            margin: 0;
            padding: 0;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .container {
            background: #111;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(255, 255, 255, 0.1);
            width: 100%;
            max-width: 600px;
            text-align: center;
        }

        .title {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 20px;
            color: #fff;
            text-transform: uppercase;
            letter-spacing: 1.5px;
        }

        label {
            display: block;
            margin: 10px 0 5px;
            font-weight: bold;
            color: #ddd;
        }

        input, textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 2px solid #555;
            border-radius: 8px;
            font-size: 16px;
            background: #222;
            color: #fff;
            outline: none;
        }

        input:focus, textarea:focus {
            border-color: #fff;
        }

        .slider {
            width: 100%;
            margin-bottom: 10px;
        }

        button {
            background-color: #fff;
            color: #000;
            font-size: 18px;
            padding: 12px 30px;
            border: none;
            border-radius: 50px;
            cursor: pointer;
            transition: background-color 0.3s;
            font-weight: bold;
        }

        button:hover {
            background-color: #ddd;
        }

        .back-button {
            display: inline-block;
            background: #555;
            color: white;
            font-size: 16px;
            padding: 10px 25px;
            border-radius: 50px;
            text-decoration: none;
            margin-top: 20px;
        }

        .back-button:hover {
            background: #777;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="title">Add New Event for <%= eventDate.format(dateFormatter) %></div>

    <form action="saveEvent" method="post">
        <input type="hidden" name="eventDate" value="<%= eventDate %>" />

        <label for="title">Event Title:</label>
        <input type="text" name="title" required placeholder="Enter event title" />

        <label for="time">Start Time - End Time:</label>
        <div>
            <input type="time" name="startTime" required />
            <span> - </span>
            <input type="time" name="endTime" required />
        </div>

        <label for="description">Description:</label>
        <textarea name="description" rows="4" placeholder="Enter event description..."></textarea>

        <label for="urgency">Urgency (1-10):</label>
        <input type="range" name="urgency" min="1" max="10" value="1" class="slider" />

        <label for="importance">Importance (1-10):</label>
        <input type="range" name="importance" min="1" max="10" value="1" class="slider" />

        <button type="submit">Save Event</button>
    </form>

    <a href="eventDetail?date=<%= eventDate %>" class="back-button">Back to Event Details</a>
</div>

</body>
</html>
