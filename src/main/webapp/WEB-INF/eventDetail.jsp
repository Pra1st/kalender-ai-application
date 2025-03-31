<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.webanwendungenmitjavahausarbeit.model.Event" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
  List<Event> events = (List<Event>) request.getAttribute("events");
  DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");
  DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Event Details</title>
  <style>
    body {
      background-color: #e0e0e0;
      font-family: 'Arial', sans-serif;
      color: #fff;
      margin: 0;
      padding: 0;
    }

    .container {
      width: 80%;
      max-width: 900px;
      margin: 50px auto;
      padding: 20px;
      background-color: #1e1e1e;
      border-radius: 10px;
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
    }

    .header {
      text-align: center;
      font-size: 2rem;
      font-weight: bold;
      margin-bottom: 30px;
    }

    .events-container {
      display: flex;
      flex-direction: column;
      gap: 15px;
      margin-bottom: 30px;
    }

    .event-info {
      background-color: #333;
      padding: 20px;
      border-radius: 8px;
      border: 1px solid #444;
      position: relative;
      transition: transform 0.2s ease;
      cursor: pointer;
    }

    .event-info:hover {
      transform: scale(1.05);
      background-color: #444;
    }

    .event-date {
      font-size: 1rem;
      color: #bbb;
    }

    .event-title {
      font-size: 1.5rem;
      font-weight: bold;
      margin-top: 10px;
      text-decoration: underline;
    }

    .event-time {
      display: block;
      font-size: 1.1rem;
      color: #888;
      margin-top: 5px;
    }

    .event-description {
      margin-top: 10px;
      font-size: 1.1rem;
    }

    .event-urgency, .event-importance {
      margin-top: 10px;
      font-size: 1.2rem;
      font-weight: bold;
    }

    .delete-button {
      background-color: #e74c3c;
      color: white;
      border: none;
      padding: 12px 20px;
      font-size: 1.1rem;
      border-radius: 8px;
      cursor: pointer;
      transition: background-color 0.3s ease, transform 0.3s ease;
      position: absolute;
      right: 20px;
      top: 20px;
    }

    .delete-button:hover {
      background-color: #c0392b;
      transform: scale(1.05);
    }

    .button-container {
      display: flex;
      justify-content: center;
      gap: 20px;
    }

    .add-button, .go-back-button {
      background-color: #444;
      color: white;
      border: none;
      padding: 12px 22px;
      font-size: 1.1rem;
      border-radius: 8px;
      cursor: pointer;
      text-decoration: none;
      width: 45%;
      text-align: center;
    }

    .add-button:hover, .go-back-button:hover {
      background-color: #555;
    }

    /* Popup Styling */
    .popup {
      display: none;
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background: rgba(0, 0, 0, 0.7);
      justify-content: center;
      align-items: center;
      z-index: 1000;
    }

    .popup-content {
      background-color: #2c2c2c;
      padding: 30px;
      border-radius: 15px;
      width: 80%;
      max-width: 600px;
      text-align: center;
      box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
      transition: transform 0.3s ease;
    }

    .popup-content h3 {
      font-size: 2rem;
      color: #f1f1f1;
      margin-bottom: 20px;
    }

    .popup-content p {
      font-size: 1.2rem;
      color: #bbb;
      margin-bottom: 20px;
    }

    .popup-content .urgency,
    .popup-content .importance {
      font-size: 1.2rem;
      color: #ccc;
      margin-top: 15px;
    }

    .popup-close {
      background-color: #444;
      padding: 12px 20px;
      color: #fff;
      font-size: 1.1rem;
      border-radius: 10px;
      cursor: pointer;
      border: none;
      transition: background-color 0.3s ease;
    }

    .popup-close:hover {
      background-color: #555;
    }
  </style>
</head>
<body>

<div class="container">
  <div class="header">
    Event Details
  </div>

  <div class="events-container">
    <% if (events != null && !events.isEmpty()) { %>
    <% for (Event event : events) { %>
    <div class="event-info" onclick="showDetails('<%= event.getTitle() %>', '<%= event.getDescription() != null ? event.getDescription() : "No description available." %>', '<%= event.getUrgency() %>', '<%= event.getImportance() %>')">
      <div class="event-date">
        <%= event.getEventDate().format(dateFormatter) %>
        <span class="event-time">
              <%= event.getStartTime() != null ? event.getStartTime().format(timeFormatter) : "N/A" %> -
              <%= event.getEndTime() != null ? event.getEndTime().format(timeFormatter) : "N/A" %>
            </span>
      </div>
      <div class="event-title">
        <%= event.getTitle() %>
      </div>

      <div class="event-urgency" style="color: <%= event.getUrgencyColor(event.getUrgency()) %>;">
        Urgency: <%= event.getUrgency() %>/10
      </div>

      <div class="event-importance" style="color: <%= event.getUrgencyColor(event.getImportance()) %>;">
        Importance: <%= event.getImportance() %>/10
      </div>

      <button class="delete-button" onclick="deleteEvent('<%= event.getId() %>')">Delete</button>
    </div>
    <% } %>
    <% } else { %>
    <p>No events scheduled for this date yet.</p>
    <% } %>
  </div>

  <!-- Button Container -->
  <div class="button-container">
    <!-- Button to Add Event (at the bottom) -->
    <a href="<%= request.getContextPath() %>/addEvent?date=<%= request.getParameter("date") %>" class="add-button">+ Add New Event</a>
    <!-- Button to Add Einkaufsliste -->
    <a href="<%= request.getContextPath() %>/einkaufsliste?date=<%= request.getParameter("date") %>&userId=<%= session.getAttribute("userId") %>" class="add-button">+ Add Einkaufsliste</a>
    <!-- Button to Get AI-Generated Events -->
    <a href="<%= request.getContextPath() %>/ai-events?date=<%= request.getParameter("date") %>&userId=<%= session.getAttribute("userId") %>" class="add-button">+ Get AI-Generated Events</a>
    <!-- Go Back Button -->
    <a href="<%= request.getContextPath() %>/calendar" class="go-back-button">Go Back to Calendar</a>
  </div>

</div>

<!-- Popup for Description -->
<div class="popup" id="popup">
  <div class="popup-content">
    <h3 id="popup-title"></h3>
    <p id="event-description"></p>
    <div id="event-urgency"></div>
    <div id="event-importance"></div>
    <button class="popup-close" onclick="closePopup()">Close</button>
  </div>
</div>

<script>
  // Show the popup with event details (title, description, urgency, importance)
  function showDetails(title, description, urgency, importance) {
    document.getElementById('popup-title').innerText = title;
    document.getElementById('event-description').innerText = description;
    document.getElementById('event-urgency').innerText = "Urgency: " + urgency + "/10";
    document.getElementById('event-importance').innerText = "Importance: " + importance + "/10";
    document.getElementById('popup').style.display = 'flex';
  }

  function deleteEvent(eventId) {
    if (confirm('Are you sure you want to delete this event?')) {
      fetch('<%= request.getContextPath() %>/eventDetail?action=deleteItem&eventid=' + eventId, {
        method: 'POST'
      }).then(response => {
        if (response.ok) location.reload();
        else alert('Failed to delete the event.');
      }).catch(error => console.error('Error:', error));
    }
  }

  function closePopup() {
    document.getElementById('popup').style.display = 'none';
  }
</script>

</body>
</html>
