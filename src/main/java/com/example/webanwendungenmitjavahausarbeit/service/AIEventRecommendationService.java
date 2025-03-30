package com.example.webanwendungenmitjavahausarbeit.service;

import com.example.webanwendungenmitjavahausarbeit.dao.EinkaufslisteDAO;
import com.example.webanwendungenmitjavahausarbeit.dao.EinkaufslisteItemDAO;
import com.example.webanwendungenmitjavahausarbeit.model.Event;
import com.example.webanwendungenmitjavahausarbeit.model.Einkaufsliste;
import com.example.webanwendungenmitjavahausarbeit.model.EinkaufslisteItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;
import java.time.LocalTime;

public class AIEventRecommendationService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("calendarPU");

    public static Event recommendEvent(String city, int people, double budget, String type, LocalDate date, LocalTime starttime,  LocalTime endtime, Long userid) {
        String prompt = "Suggest an event in " + city + " for " + people +
                " people which can be done exactly on " + date + " at " + starttime +
                " with a budget of €" + budget + ". It has to be of type: " + type +
                ".\n\nReturn the result strictly as a JSON object with this structure:\n" +
                "{\n" +
                "  \"name\": \"Event Name\",\n" +
                "  \"address\": \"Event Address\",\n" +
                "  \"price\": \"Price for a single person\"\n" +
                "}";

        String aiDescription = OpenAIService.getRecommendation(prompt);
        System.out.println("AI Response: " + aiDescription);

        JSONObject responseJson = new JSONObject(aiDescription);
        if (responseJson.has("error")) {
            return new Event(LocalDate.now(), LocalTime.now(), LocalTime.now(), "Error in event recommendation", "AI Error",0,0, userid);
        }

        JSONObject event = new JSONObject(aiDescription);
        String name = event.getString("name");
        String address = event.getString("address");
        String price = event.getString("price");

        String description = "You can go to "+ address+ " and experience "+name+" for "+price;

        Event aiEvent = new Event(date, starttime, endtime, description, name,0,0, userid);

        saveAIEvent(aiEvent);

        return aiEvent;
    }

    public static Einkaufsliste recommendEinkaufsliste(String gericht,LocalDate date, int price, String listIdParam, String userid) {
        EinkaufslisteItemDAO itemDAO = new EinkaufslisteItemDAO();
        EinkaufslisteDAO einkaufslisteDAO = new EinkaufslisteDAO();
        String prompt = "Create a shopping list in JSON format for the dish '{gericht}'" +
                " with a total budget of {preis} EUR. " +
                "The response should include the item names and their amounts." +
                " Format the output as a JSON array, where each item contains the following structure:" +
                " 'item': [itemname] - [amount].";
        prompt = prompt.replace("{gericht}", gericht).replace("{preis}", String.valueOf(price));

        String aiDescription = OpenAIService.getRecommendation(prompt);
        System.out.println("AI Response: " + aiDescription);
        Long Userid = Long.parseLong(userid);
        try {
            JSONArray itemsArray = new JSONArray(aiDescription);

            Long listId = Long.parseLong(listIdParam);

            Einkaufsliste einkaufliste = einkaufslisteDAO.getEinkaufsliste(listId);
            System.out.println("Einkaufsliste saved!");
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject itemObject = itemsArray.getJSONObject(i);
                String itemName = itemObject.getString("item");
                System.out.println("XXX ITEM NAME: " + itemName);
                EinkaufslisteItem einkaufsItem = new EinkaufslisteItem(einkaufliste,itemName);
                itemDAO.addItem(einkaufsItem);

            }

            String description = "Your recommended shopping list for " + gericht + " within a budget of " + price + " EUR";
            String name = "Einkaufsliste for " + gericht;


            saveAIListe(einkaufliste);
            return einkaufliste;

        } catch (Exception e) {
            System.err.println("Error processing AI response: " + e.getMessage());
            return new Einkaufsliste(Userid, date,gericht);
        }
    }

    public static void saveAIEvent(Event event) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(event);
        em.getTransaction().commit();
        em.close();
    }

    public static void saveAIListe(Einkaufsliste einkaufsliste) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(einkaufsliste);
        em.getTransaction().commit();
        em.close();
    }

    public static List<Event> getSavedAIEvents() {
        EntityManager em = emf.createEntityManager();
        List<Event> aiEvents = em.createQuery("SELECT e FROM Event e WHERE e.userid = 0", Event.class).getResultList();
        em.close();
        return aiEvents;
    }
}