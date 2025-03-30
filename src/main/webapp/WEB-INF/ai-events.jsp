<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="com.example.webanwendungenmitjavahausarbeit.model.Event" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="com.example.webanwendungenmitjavahausarbeit.service.AIEventRecommendationService" %>
<!DOCTYPE html>
<html lang="de">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>AI Events</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <style>
    body {
      background-color: #121212;
      color: #fff;
      font-family: 'Arial', sans-serif;
    }
    h2, h3 {
      font-weight: 600;
    }
    .container {
      max-width: 800px;
    }
    .form-label {
      font-size: 1.1rem;
    }
    .form-control {
      background-color: #333;
      color: #fff;
      border: 1px solid #444;
    }
    .form-control option {
      background-color: #333;
      color: #fff;
    }
    .form-control:focus {
      border-color: #777;
      background-color: #444;
    }
    .btn {
      border-radius: 20px;
      font-weight: bold;
    }
    .btn-primary {
      background-color: #222;
      border-color: #444;
    }
    .btn-primary:hover {
      background-color: #444;
    }
    .btn-success {
      background-color: #28a745;
      border-color: #218838;
    }
    .btn-success:hover {
      background-color: #218838;
    }
    .btn-secondary {
      background-color: #6c757d;
      border-color: #5a6268;
    }
    .btn-secondary:hover {
      background-color: #5a6268;
    }
    .list-group-item {
      background-color: #333;
      border: none;
      color: #ccc;
    }
    .list-group-item:hover {
      background-color: #444;
      cursor: pointer;
    }
    .list-group-item strong {
      color: #fff;
    }
    .mt-3, .mt-4 {
      margin-top: 2rem !important;
    }
    .d-flex {
      flex-wrap: wrap;
      gap: 10px;
    }
    .form-control, .btn, select {
      margin-bottom: 10px;
    }
  </style>
</head>
<body>

<div class="container mt-5">
  <h2>🤖 AI-Generated Events</h2>

  <!-- AI Event Recommendation Form -->
  <form action="${pageContext.request.contextPath}/ai-events" method="post">
    <input type="hidden" name="date" value="<%= request.getParameter("date") %>">
    <input type="hidden" name="userId" value="<%= request.getParameter("userId") %>">

    <div class="mb-3">
      <label class="form-label">City:</label>
      <input type="text" class="form-control" name="city" required>
    </div>
    <div class="mb-3">
      <label class="form-label">Number of People:</label>
      <input type="number" class="form-control" name="people" required>
    </div>
    <div class="mb-3">
      <label class="form-label">Budget (€):</label>
      <input type="number" step="0.01" class="form-control" name="budget" required>
    </div>
    <div class="mb-3">
      <label class="form-label">Event Type:</label>
      <input type="text" class="form-control" name="type" required>
    </div>
    <div class="mb-3">
      <label class="form-label">Event Time (Start - End):</label>
      <div class="d-flex">
        <!-- Start Time Dropdown -->
        <select class="form-control" name="startHour" required>
          <option value="" disabled selected>Start Hour</option>
          <option value="00">00</option>
          <option value="01">01</option>
          <option value="02">02</option>
          <option value="03">03</option>
          <option value="04">04</option>
          <option value="05">05</option>
          <option value="06">06</option>
          <option value="07">07</option>
          <option value="08">08</option>
          <option value="09">09</option>
          <option value="10">10</option>
          <option value="11">11</option>
          <option value="12">12</option>
          <option value="13">13</option>
          <option value="14">14</option>
          <option value="15">15</option>
          <option value="16">16</option>
          <option value="17">17</option>
          <option value="18">18</option>
          <option value="19">19</option>
          <option value="20">20</option>
          <option value="21">21</option>
          <option value="22">22</option>
          <option value="23">23</option>
        </select>

        <select class="form-control" name="startMinute" required>
          <option value="" disabled selected>Start Minute</option>
          <option value="00">00</option>
          <option value="15">15</option>
          <option value="30">30</option>
          <option value="45">45</option>
        </select>

        <span class="mx-2">-</span> <!-- Separator between start and end time -->

        <!-- End Time Dropdown -->
        <select class="form-control" name="endHour" required>
          <option value="" disabled selected>End Hour</option>
          <option value="00">00</option>
          <option value="01">01</option>
          <option value="02">02</option>
          <option value="03">03</option>
          <option value="04">04</option>
          <option value="05">05</option>
          <option value="06">06</option>
          <option value="07">07</option>
          <option value="08">08</option>
          <option value="09">09</option>
          <option value="10">10</option>
          <option value="11">11</option>
          <option value="12">12</option>
          <option value="13">13</option>
          <option value="14">14</option>
          <option value="15">15</option>
          <option value="16">16</option>
          <option value="17">17</option>
          <option value="18">18</option>
          <option value="19">19</option>
          <option value="20">20</option>
          <option value="21">21</option>
          <option value="22">22</option>
          <option value="23">23</option>
        </select>

        <select class="form-control" name="endMinute" required>
          <option value="" disabled selected>End Minute</option>
          <option value="00">00</option>
          <option value="15">15</option>
          <option value="30">30</option>
          <option value="45">45</option>
        </select>
      </div>
    </div>

    <button type="submit" class="btn btn-primary w-100">Get AI Event Suggestion</button>

  </form>

  <!-- Show Saved AI Events -->
  <h3 class="mt-4">📌 Saved AI Events</h3>
  <ul class="list-group">
    <%
      String dateParam = request.getParameter("date"); // Get the date from the URL
      LocalDate selectedDate = LocalDate.parse(dateParam); // Parse the date

      List<Event> savedAIEvents = AIEventRecommendationService.getSavedAIEvents();

      if (savedAIEvents == null || savedAIEvents.isEmpty()) {
    %>
    <li class="list-group-item text-muted">No saved AI events yet.</li>
    <%
    } else {
      // Filter events to show only those matching the selected date
      List<Event> filteredEvents = savedAIEvents.stream()
              .filter(event -> event.getEventDate().equals(selectedDate))
              .collect(Collectors.toList());

      if (filteredEvents.isEmpty()) {
    %>
    <li class="list-group-item text-muted">No saved AI events for this date.</li>
    <%
    } else {
      for (Event event : filteredEvents) {
    %>
    <li class="list-group-item d-flex justify-content-between align-items-center">
      <div>
        <strong><%= event.getTitle() %></strong>: <%= event.getDescription() %>,
        <%= event.getEventDate() %>, from <%= event.getStartTime() %> to <%= event.getEndTime() %> .
      </div>

      <!-- Save as Event Button (Hidden Form) -->
      <form action="${pageContext.request.contextPath}/save-ai-event" method="post" style="margin: 0;">
        <input type="hidden" name="date" value="<%= request.getParameter("date") %>">
        <input type="hidden" name="userId" value="<%= request.getParameter("userId") %>">
        <input type="hidden" name="title" value="<%= event.getTitle() %>">
        <input type="hidden" name="location" value="<%= event.getDescription() %>">
        <input type="hidden" name="participants" value="10"> <!-- Default participants -->
        <input type="hidden" name="budget" value="50.00"> <!-- Default budget -->
        <input type="hidden" name="eventDate" value="<%= event.getEventDate() %>">
        <input type="hidden" name="startTime" value="<%= event.getStartTime() %>">
        <input type="hidden" name="endTime" value="<%= event.getEndTime() %>">
        <button type="submit" class="btn btn-success btn-sm">Save as Event</button>
      </form>
    </li>
    <%
          }
        }
      }
    %>
  </ul>

  <a href="${pageContext.request.contextPath}/calendar" class="btn btn-secondary mt-3">⬅ Back to Calendar</a>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
