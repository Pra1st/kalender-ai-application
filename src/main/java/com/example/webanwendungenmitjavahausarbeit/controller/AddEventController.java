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

@WebServlet("/addEvent")
public class AddEventController extends HttpServlet {
    // redirect to jsp
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/addEvent.jsp").forward(request, response);
    }
}