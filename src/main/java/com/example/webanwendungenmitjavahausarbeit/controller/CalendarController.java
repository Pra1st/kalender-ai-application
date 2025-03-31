package com.example.webanwendungenmitjavahausarbeit.controller;

import com.example.webanwendungenmitjavahausarbeit.dao.EventDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/calendar")
public class CalendarController extends HttpServlet {
    private EventDAO eventDAO = new EventDAO();
    //displays the calendar.sjp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/calendar.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        eventDAO.close();
    }
}
