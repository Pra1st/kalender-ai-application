package com.example.webanwendungenmitjavahausarbeit.dao;

import com.example.webanwendungenmitjavahausarbeit.model.Einkaufsliste;
import com.example.webanwendungenmitjavahausarbeit.model.EinkaufslisteItem;
import com.example.webanwendungenmitjavahausarbeit.model.Event;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

public class EventDAO {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("calendarPU");

    // add a new Event
    public void addEvent(Event event) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(event);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    //get all Events of a surtain Date and userid
    public List<Event> getEventsByDate(LocalDate date, Long userid) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Event> events = null;

        try {
            TypedQuery<Event> query = entityManager.createQuery(
                    "SELECT e FROM Event e WHERE e.eventDate = :eventDate AND e.userid = :userid", Event.class);
            query.setParameter("eventDate", date);
            query.setParameter("userid", userid);
            events = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return events;
    }

    //get all events
    public List<Event> getAllEvents() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Event> events = null;

        try {
            TypedQuery<Event> query = entityManager.createQuery("SELECT e FROM Event e", Event.class);
            events = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return events;
    }

    //delete a specific event
    public void deleteEvent(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Event event = em.find(Event.class, id);

            if (event != null) {
                em.remove(event);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    //close the entityManagerFactory Connection
    public void close() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
