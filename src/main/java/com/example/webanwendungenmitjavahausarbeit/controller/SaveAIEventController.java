package com.example.webanwendungenmitjavahausarbeit.controller;

import com.example.webanwendungenmitjavahausarbeit.service.AIEventRecommendationService;
import com.example.webanwendungenmitjavahausarbeit.model.Event;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.IOException;

@WebServlet("/save-ai-event")
public class SaveAIEventController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("title");
        String location = request.getParameter("location");
        int participants = Integer.parseInt(request.getParameter("participants"));
        double budget = Double.parseDouble(request.getParameter("budget"));
        String eventDateStr = request.getParameter("eventDate");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");
        Long userId = (Long) request.getSession().getAttribute("userId");

        LocalDate eventDate = LocalDate.parse(eventDateStr);
        LocalTime startTime = LocalTime.parse(startTimeStr);
        LocalTime endTime = LocalTime.parse(endTimeStr);

        String description = "AI-Suggested Event: " + location +
        "Participants: " + participants + " and a Budget of " + budget + "€.";

        Event event = new Event(eventDate, startTime, endTime, description, title,0,0, userId);

        AIEventRecommendationService.saveAIEvent(event);

        request.getRequestDispatcher("/WEB-INF/ai-events.jsp").forward(request, response);
    }
}