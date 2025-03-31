package com.example.webanwendungenmitjavahausarbeit.controller;

import com.example.webanwendungenmitjavahausarbeit.model.Event;
import com.example.webanwendungenmitjavahausarbeit.service.EventService;
import com.example.webanwendungenmitjavahausarbeit.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/saveEvent")
public class SaveEventController extends HttpServlet {
    private EventService eventService = new EventService();
    private UserService userService = new UserService();

    // saves a Event
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dateParam = request.getParameter("eventDate");
        LocalDate eventDate = LocalDate.parse(dateParam);
        Long userId = (Long) request.getSession().getAttribute("userId");
        String startTimeParam = request.getParameter("startTime");
        String endTimeParam = request.getParameter("endTime");
        String description = request.getParameter("description");
        String title = request.getParameter("title");  // Capture the title parameter
        int urgency = Integer.parseInt(request.getParameter("urgency"));
        int importance = Integer.parseInt(request.getParameter("importance"));
        LocalTime startTime = LocalTime.parse(startTimeParam);
        LocalTime endTime = LocalTime.parse(endTimeParam);

        Event newEvent = new Event(eventDate, startTime, endTime, description, title,urgency, importance, userId);

        eventService.addEvent(newEvent);

        response.sendRedirect("eventDetail?date=" + eventDate);
    }
}
