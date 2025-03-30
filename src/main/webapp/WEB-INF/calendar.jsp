<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.LocalDate, java.time.YearMonth" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.example.webanwendungenmitjavahausarbeit.service.EventService" %>
<%@ page import="com.example.webanwendungenmitjavahausarbeit.model.Event" %>
<%@ page import="java.util.List" %>

<%
  // Check if user is logged in
  String loggedInUser = (String) session.getAttribute("username");

  // Get userId from session
  Long userId = (Long) session.getAttribute("userId");

  // If userId is null, they are not logged in, so we can display the login button
  if (userId == null) {
    // Redirect or handle accordingly if user is not logged in
    out.println("<script>window.location.href = 'login';</script>");
    return;
  }

  // Get current date or user-selected date
  String selectedYear = request.getParameter("year");
  String selectedMonth = request.getParameter("month");

  int year = (selectedYear != null) ? Integer.parseInt(selectedYear) : LocalDate.now().getYear();
  int month = (selectedMonth != null) ? Integer.parseInt(selectedMonth) : LocalDate.now().getMonthValue();

  YearMonth yearMonth = YearMonth.of(year, month);
  int daysInMonth = yearMonth.lengthOfMonth();
  int firstDayOfWeek = LocalDate.of(year, month, 1).getDayOfWeek().getValue(); // 1 = Monday, 7 = Sunday

  // Event service instance
  EventService eventService = new EventService();

  // Get today's date and tomorrow's date for notifications
  LocalDate today = LocalDate.now();
  LocalDate tomorrow = today.plusDays(1);

  // Retrieve events for today and tomorrow
  List<Event> todayEvents = eventService.getEventsByDate(today, userId);
  List<Event> tomorrowEvents = eventService.getEventsByDate(tomorrow, userId);
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Calendar</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f4;
      margin: 0;
      display: flex;
      justify-content: center;
      align-items: flex-start;
      height: 100vh;
      padding: 20px;
      box-sizing: border-box;
    }

    .main-container {
      display: flex;
      justify-content: space-between;
      width: 100%;
      max-width: 1400px;
      gap: 40px;
    }

    .calendar-container {
      width: 70%;
      background-color: white;
      border-radius: 8px;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
      padding: 40px 30px;
    }

    .notifications-container {
      width: 28%;
      background-color: white;
      border-radius: 8px;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
      padding: 40px 30px;
    }

    h1 {
      margin-bottom: 10px;
      text-align: center;
    }

    .controls {
      margin-bottom: 20px;
      display: flex;
      justify-content: center;
      align-items: center;
      gap: 20px;
    }

    .controls label {
      font-size: 16px;
    }

    .controls select {
      font-size: 16px;
      padding: 10px;
      border: 2px solid black;
      border-radius: 5px;
      background-color: white;
      color: black;
      appearance: none;
    }

    .current-day {
      border: 2px solid black;
      background-color: #f0f0f0;
      font-weight: bold;
    }

    .controls button {
      font-size: 16px;
      padding: 10px 25px;
      background-color: black;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }

    .controls button:hover {
      background-color: #444;
    }

    .login-btn {
      position: absolute;
      top: 20px;
      right: 20px;
      padding: 12px 25px;
      font-size: 16px;
      background-color: black;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
    }

    .login-btn:hover {
      background-color: #444;
    }

    table {
      width: 100%;
      margin: auto;
      border-collapse: collapse;
      background: white;
    }

    th, td {
      width: 14%;
      height: 90px;
      border: 1px solid #ddd;
      text-align: center;
      font-size: 20px;
    }

    th {
      background-color: black;
      color: white;
    }

    td a {
      text-decoration: none;
      color: black;
      font-weight: bold;
      display: block;
      width: 100%;
      height: 100%;
      line-height: 90px;
    }

    td a:hover {
      background-color: #ddd;
    }

    .empty {
      background-color: #f9f9f9;
    }

    td {
      position: relative; /* Ensure position relative to the table cell */
      width: 14%;
      height: 100px; /* Increase the height of the cell to avoid overlap */
      border: 1px solid #ddd;
      text-align: center;
      font-size: 18px;
    }

    .event-dots {
      position: absolute;
      top: 80px; /* Adjust this value so dots are below the date */
      left: 50%;
      transform: translateX(-50%);
      display: flex;
      justify-content: center;
      gap: 5px; /* Space between the dots */
    }

    .event-dot {
      width: 8px;
      height: 8px;
      border-radius: 50%;
    }

    .notifications {
      background-color: white;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    }

    .notification-header {
      font-size: 22px;
      margin-bottom: 15px;
    }

    .notification-list {
      list-style-type: none;
      padding-left: 0;
    }

    .notification-item {
      background-color: #f9f9f9;
      padding: 12px;
      margin-bottom: 15px;
      border-radius: 6px;
      font-size: 18px;
    }

    .notification-item span {
      font-weight: bold;
    }
  </style>
</head>
<body>

<%
  if (loggedInUser == null) {
%>
<a href="login" class="login-btn">Login</a>
<%
} else {
%>
<a href="logout" class="login-btn">Logout (<%= loggedInUser %>)</a>
<%
  }
%>

<div class="main-container">

  <div class="calendar-container">
    <h1>Calendar</h1>

    <form method="get" action="calendar">
      <div class="controls">
        <label for="year">Year:</label>
        <select name="year" id="year">
          <% for (int y = 2020; y <= 2030; y++) { %>
          <option value="<%= y %>" <%= (y == year) ? "selected" : "" %>><%= y %></option>
          <% } %>
        </select>

        <label for="month">Month:</label>
        <select name="month" id="month">
          <% for (int m = 1; m <= 12; m++) { %>
          <option value="<%= m %>" <%= (m == month) ? "selected" : "" %>><%= java.time.Month.of(m) %></option>
          <% } %>
        </select>

        <button type="submit">Go</button>
      </div>
    </form>

    <table>
      <tr>
        <th>Mon</th><th>Tue</th><th>Wed</th><th>Thu</th><th>Fri</th><th>Sat</th><th>Sun</th>
      </tr>
      <tr>
        <%
          int dayCounter = 1;
          LocalDate currentDate = LocalDate.now();
          for (int i = 1; i <= 42; i++) {
            if (i < firstDayOfWeek || dayCounter > daysInMonth) {
        %>
        <td class="empty"></td>
        <%
        } else {
          LocalDate currentDay = LocalDate.of(year, month, dayCounter);
          String currentDayClass = currentDate.equals(currentDay) ? "current-day" : "";
          List<Event> eventsForDay = eventService.getEventsByDate(currentDay, userId);
        %>
        <td class="<%= currentDayClass %>">
          <a href="eventDetail?date=<%= year %>-<%= String.format("%02d", month) %>-<%= String.format("%02d", dayCounter) %>">
            <%= dayCounter %>
          </a>

          <!-- Display event dots below the date -->
          <div class="event-dots">
            <%
              if (eventsForDay != null && !eventsForDay.isEmpty()) {
                for (Event event : eventsForDay) {
                  String urgencyColor = event.getUrgencyColor(event.getImportance()); // Get color based on importance
            %>
            <div class="event-dot" style="background-color:<%= urgencyColor %>;"></div>
            <%
                }
              }
            %>
          </div>
        </td>
        <%
            dayCounter++;
          }
          if (i % 7 == 0) {
        %>
      </tr><tr>
      <%
          }
        }
      %>
    </tr>
    </table>
  </div>

  <div class="notifications-container">
    <div class="notifications">
      <div class="notification-header">
        Notifications
      </div>

      <ul class="notification-list">
        <% for (Event event : todayEvents) { %>
        <li class="notification-item">
          <span>Today:</span> <%= event.getTitle() %> at <%= event.getStartTime() %>
        </li>
        <% } %>

        <% for (Event event : tomorrowEvents) { %>
        <li class="notification-item">
          <span>Tomorrow:</span> <%= event.getTitle() %> at <%= event.getStartTime() %>
        </li>
        <% } %>
      </ul>
    </div>
  </div>

</div>

</body>
</html>
