package com.example.webanwendungenmitjavahausarbeit.controller;

import com.example.webanwendungenmitjavahausarbeit.dao.EinkaufslisteItemDAO;
import com.example.webanwendungenmitjavahausarbeit.dao.EventDAO;
import com.example.webanwendungenmitjavahausarbeit.model.Einkaufsliste;
import com.example.webanwendungenmitjavahausarbeit.model.EinkaufslisteItem;
import com.example.webanwendungenmitjavahausarbeit.model.Event;
import com.example.webanwendungenmitjavahausarbeit.service.EventService;
import com.example.webanwendungenmitjavahausarbeit.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/eventDetail")
public class EventDetailController extends HttpServlet {
    private EventService eventService = new EventService();  // EventService handles business logic
    private UserService userService = new UserService();
    private EventDAO eventDAO = new EventDAO();
    @Override
    // Display the events
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dateParam = request.getParameter("date");
        String action = request.getParameter("action");
        Long userId = (Long) request.getSession().getAttribute("userId");

        if (userId == null) {
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);  // Redirect if not logged in
            return;
        }




        if (dateParam != null && !dateParam.isEmpty()) {
            try {
                LocalDate eventDate = LocalDate.parse(dateParam);
                List<Event> events = eventService.getEventsByDate(eventDate, userId);
                request.setAttribute("events", events.isEmpty() ? List.of() : events);
            } catch (Exception e) {
                request.setAttribute("error", "Invalid date format. Please use yyyy-MM-dd.");
                e.printStackTrace();
            }
        } else {
            request.setAttribute("error", "Date parameter is missing.");
        }
        request.getRequestDispatcher("/WEB-INF/eventDetail.jsp").forward(request, response);
    }
    // delete existing event
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String userId = request.getParameter("userId");
        String dateParam = request.getParameter("date");
        try {
            if ("deleteItem".equals(action)) {
                String itemIdParam = request.getParameter("eventid");
                if (itemIdParam != null && !itemIdParam.isEmpty()) {
                    System.out.println("Event NUMBER TO DELETE: " + itemIdParam);
                    Long itemId = Long.parseLong(itemIdParam);
                    eventDAO.deleteEvent(itemId);
                }
            }
            request.getRequestDispatcher("/WEB-INF/eventDetail.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request: " + e.getMessage());
        }
    }

}