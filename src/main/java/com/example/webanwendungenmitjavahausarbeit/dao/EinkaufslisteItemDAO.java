package com.example.webanwendungenmitjavahausarbeit.dao;

import com.example.webanwendungenmitjavahausarbeit.model.EinkaufslisteItem;
import com.example.webanwendungenmitjavahausarbeit.model.Einkaufsliste;
import jakarta.persistence.*;

public class EinkaufslisteItemDAO {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("calendarPU");

    //Adds a einkauflistenitem
    public void addItem(EinkaufslisteItem item) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(item);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    //deletes a einkaufsliste item
    public void deleteItem(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            EinkaufslisteItem item = em.find(EinkaufslisteItem.class, id);
            if (item != null) {
                Einkaufsliste einkaufsliste = item.getEinkaufsliste();

                einkaufsliste.getItems().remove(item);
                em.merge(einkaufsliste);
                System.out.println("Deleting item: " + item.getItem() + " (ID: " + item.getId() + ")");
                em.remove(item);
                em.flush();
                System.out.println("Item removed, flushing changes...");
            } else {
                System.out.println("Item with ID " + id + " not found.");
            }
            transaction.commit();
            System.out.println("Transaction committed.");
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
