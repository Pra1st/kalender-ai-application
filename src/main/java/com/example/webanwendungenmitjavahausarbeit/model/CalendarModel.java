package com.example.webanwendungenmitjavahausarbeit.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CalendarModel {
    private int year;
    private int month;
    private Map<Integer, List<Event>> events = new HashMap<>();

    public CalendarModel(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public void addEvent(int day, Event event) {
        events.computeIfAbsent(day, k -> new ArrayList<>()).add(event);
    }

    public List<Event> getEvents(int day) {
        return events.getOrDefault(day, new ArrayList<>());
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
