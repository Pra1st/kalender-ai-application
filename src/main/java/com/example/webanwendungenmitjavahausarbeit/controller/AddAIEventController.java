package com.example.webanwendungenmitjavahausarbeit.controller;

import com.example.webanwendungenmitjavahausarbeit.dao.EventDAO;
import com.example.webanwendungenmitjavahausarbeit.model.Event;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/addAIEvent")
public class AddAIEventController extends HttpServlet {
    private EventDAO eventDAO = new EventDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdParam = request.getParameter("userId");
        String dateParam = request.getParameter("eventDate");
        String title = request.getParameter("title");
        String startTimeParam = request.getParameter("startTime");
        String endTimeParam = request.getParameter("endTime");
        String description = request.getParameter("description");
        int urgency = Integer.parseInt(request.getParameter("urgency"));
        int importance = Integer.parseInt(request.getParameter("importance"));
        Long userId = Long.parseLong(userIdParam);
        if (dateParam != null && !dateParam.isEmpty() && title != null && !title.isEmpty()) {
            LocalDate eventDate = LocalDate.parse(dateParam);
            LocalTime startTime = LocalTime.parse(startTimeParam, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime endTime = LocalTime.parse(endTimeParam, DateTimeFormatter.ofPattern("HH:mm"));

            Event aiEvent = new Event(eventDate, startTime, endTime, description,title,urgency,importance,userId);
            eventDAO.addEvent(aiEvent);
        }

        response.sendRedirect(request.getContextPath() + "/eventDetail?date=" + dateParam);
    }
}