package com.example.webanwendungenmitjavahausarbeit.dao;

import com.example.webanwendungenmitjavahausarbeit.model.Einkaufsliste;
import com.example.webanwendungenmitjavahausarbeit.model.EinkaufslisteItem;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class EinkaufslisteDAO {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("calendarPU");

    //adds a new einkaufliste into the db
    public void addEinkaufsliste(Einkaufsliste einkaufsliste) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(einkaufsliste);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // get a einkafusliste by id
    public Einkaufsliste getEinkaufsliste(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Einkaufsliste einkaufsliste = em.find(Einkaufsliste.class, id);
        em.close();
        return einkaufsliste;
    }

    // get a einkaufsliste by userid and date
    public List<Einkaufsliste> getEinkaufslistenByUserAndDate(Long userId, LocalDate date) {
        EntityManager em = entityManagerFactory.createEntityManager();

        System.out.println("Fetching Einkaufslisten for userId: " + userId + " and date: " + date);

        List<Einkaufsliste> listen = em.createQuery(
                        "SELECT e FROM Einkaufsliste e WHERE e.userId = :userId AND e.datum = :datum",
                        Einkaufsliste.class)
                .setParameter("userId", userId)
                .setParameter("datum", date)
                .getResultList();

        if (listen.isEmpty()) {
            System.out.println("No Einkaufslisten found for userId: " + userId + " and date: " + date);
        } else {
            System.out.println("Found " + listen.size() + " Einkaufslisten for userId: " + userId + " and date: " + date);
        }

        em.close();
        return listen;
    }

    //delete a einkaufliste and all items within
    public void deleteEinkaufsliste(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Einkaufsliste einkaufsliste = em.find(Einkaufsliste.class, id);

            if (einkaufsliste != null) {
                for (EinkaufslisteItem item : einkaufsliste.getItems()) {
                    em.remove(item);
                }
                em.remove(einkaufsliste);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
