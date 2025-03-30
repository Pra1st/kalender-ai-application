package com.example.webanwendungenmitjavahausarbeit.service;

import com.example.webanwendungenmitjavahausarbeit.model.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import com.example.webanwendungenmitjavahausarbeit.dao.EventDAO;
import com.example.webanwendungenmitjavahausarbeit.model.Event;

public class EventService {

    private EventDAO eventDAO = new EventDAO();


    public void addEvent(Event event) {
        eventDAO.addEvent(event);
    }

    public Event getEventByDate(String dateString, Long userid) {
        if (dateString != null && !dateString.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adjust the format as per your date format
            LocalDate eventDate = LocalDate.parse(dateString, formatter);

            return getEventByDate(eventDate, userid);
        }
        return null;
    }

    public Event getEventByDate(LocalDate eventDate, Long userId) {
        List<Event> events = eventDAO.getEventsByDate(eventDate, userId);
        if (events != null && !events.isEmpty()) {
            return events.get(0);
        }
        return null;
    }

    public List<Event> getEventsByDate(LocalDate eventDate, Long userid) {
        return eventDAO.getEventsByDate(eventDate, userid);
    }


    public List<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }
}