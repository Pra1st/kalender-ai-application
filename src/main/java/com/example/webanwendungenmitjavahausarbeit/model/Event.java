package com.example.webanwendungenmitjavahausarbeit.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "user_id", nullable = false)
    private Long userid;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 255)
    private int importance;

    @Column(nullable = false, length = 255)
    private int urgency;

    // Constructor with parameters
    public Event(LocalDate eventDate, LocalTime startTime, LocalTime endTime, String description, String title, int urgency, int importance, Long userid) {
        this.eventDate = eventDate != null ? eventDate : LocalDate.now();
        this.startTime = startTime != null ? startTime : LocalTime.now();
        this.endTime = endTime != null ? endTime : LocalTime.now();
        this.description = description != null ? description : "";
        this.title = title != null ? title : "";
        this.userid = userid;
        this.importance = importance;
        this.urgency = urgency;
    }

    // Default constructor
    public Event() {
        this.eventDate = LocalDate.now();
        this.startTime = LocalTime.now();
        this.endTime = LocalTime.now();
        this.description = "";
        this.title = "";
        this.userid= 0L;
        this.importance = 0;
        this.urgency = 0;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate != null ? eventDate : LocalDate.now();
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime != null ? startTime : LocalTime.now();
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime != null ? endTime : LocalTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description != null ? description : "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title != null ? title : "";
    }

    public Long getUserid() {
        return userid;
    }
    public void setUserid(int userid) {}

    public int getImportance() {
        return importance;
    }
    public void setImportance(int importance) {
        this.importance = importance;
    }

    public int getUrgency() {return urgency;}

    public void setUrgency(int urgency) {this.urgency = urgency;}

    public String getUrgencyColor(int urgency) {
        int red = (int) ((urgency - 1) * (255.0 / 9));
        int green = (int) ((10 - urgency) * (255.0 / 9));
        return "rgb(" + red + "," + green + ",0)";
    }


    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventDate=" + eventDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", userid=" + userid +
                '}';
    }
}
