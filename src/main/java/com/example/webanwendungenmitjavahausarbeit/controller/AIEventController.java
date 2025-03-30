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
import java.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.List;

@WebServlet("/ai-events")
public class AIEventController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = 0L; //(Long) request.getSession().getAttribute("userId");
        String city = request.getParameter("city");
        int people = Integer.parseInt(request.getParameter("people"));
        double budget = Double.parseDouble(request.getParameter("budget"));

        String type = request.getParameter("type");
        String dateStr = request.getParameter("date");
        System.out.println(dateStr);

        String startHour = request.getParameter("startHour");
        String startMinute = request.getParameter("startMinute");
        String endHour = request.getParameter("endHour");
        String endMinute = request.getParameter("endMinute");

        String startTimeString = String.format("%02d:%02d", Integer.parseInt(startHour), Integer.parseInt(startMinute));
        String endTimeString = String.format("%02d:%02d", Integer.parseInt(endHour), Integer.parseInt(endMinute));

        LocalTime startTime = LocalTime.parse(startTimeString);
        LocalTime endTime = LocalTime.parse(endTimeString);

        LocalDate date = null;

        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = LocalDate.parse(dateStr, dateFormatter);
        } catch (Exception e) {
            e.printStackTrace();
        }


        AIEventRecommendationService.recommendEvent(city, people, budget, type, date, startTime,endTime,userId );

        request.getRequestDispatcher("/WEB-INF/ai-events.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Event> savedAIEvents = AIEventRecommendationService.getSavedAIEvents();

        request.setAttribute("savedAIEvents", savedAIEvents);
        request.getRequestDispatcher("/WEB-INF/ai-events.jsp").forward(request, response);
    }
}