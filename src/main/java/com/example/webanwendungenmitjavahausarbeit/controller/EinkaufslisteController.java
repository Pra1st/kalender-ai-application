package com.example.webanwendungenmitjavahausarbeit.controller;

import com.example.webanwendungenmitjavahausarbeit.dao.EinkaufslisteDAO;
import com.example.webanwendungenmitjavahausarbeit.dao.EinkaufslisteItemDAO;
import com.example.webanwendungenmitjavahausarbeit.model.Einkaufsliste;
import com.example.webanwendungenmitjavahausarbeit.model.EinkaufslisteItem;
import com.example.webanwendungenmitjavahausarbeit.service.AIEventRecommendationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/einkaufsliste")
public class EinkaufslisteController extends HttpServlet {

    private EinkaufslisteDAO einkaufslisteDAO = new EinkaufslisteDAO();
    private EinkaufslisteItemDAO itemDAO = new EinkaufslisteItemDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdParam = request.getParameter("userId");
        String dateParam = request.getParameter("date");

        if (userIdParam == null || userIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing userId parameter.");
            return;
        }

        try {
            Long userId = Long.parseLong(userIdParam);

            LocalDate filterDate = LocalDate.now();
            System.out.println("Received date parameter: " + dateParam);

            if (dateParam != null && !dateParam.isEmpty()) {
                try {
                    filterDate = LocalDate.parse(dateParam);
                    System.out.println("Parsed date: " + filterDate);
                } catch (Exception e) {
                    System.err.println("Invalid date format provided: " + dateParam);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format.");
                    return;
                }
            }

            System.out.println("Using filter date: " + filterDate);

            List<Einkaufsliste> listen = einkaufslisteDAO.getEinkaufslistenByUserAndDate(userId, filterDate);
            System.out.println("Found " + (listen != null ? listen.size() : 0) + " Einkaufslisten for userId: " + userId + " and date: " + filterDate);

            request.setAttribute("einkaufslisten", listen);

            request.getRequestDispatcher("/WEB-INF/einkaufsliste.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String userId = request.getParameter("userId"); // Ensure userId is passed in the redirect
        String dateParam = request.getParameter("date");
        System.out.println("XXX Received parameters: " + action + " " +userId+ " " +dateParam);
        try {
            if ("deleteItem".equals(action)) {
                String itemIdParam = request.getParameter("itemId");

                if (itemIdParam != null && !itemIdParam.isEmpty()) {
                    System.out.println("ITEM NUMBER TO DELETE: " + itemIdParam);
                    Long itemId = Long.parseLong(itemIdParam);
                    itemDAO.deleteItem(itemId);
                }
            } else if ("deleteList".equals(action)) {
                String listIdParam = request.getParameter("listId");
                if (listIdParam != null && !listIdParam.isEmpty()) {
                    System.out.println("list id : " + listIdParam);
                    Long listId = Long.parseLong(listIdParam);
                    einkaufslisteDAO.deleteEinkaufsliste(listId);
                }
            } else if ("aiList".equals(action)){
                System.out.println("Ai List");
                String gericht = request.getParameter("gericht");
                System.out.println(gericht);
                int price = Integer.parseInt(request.getParameter("preis"));
                System.out.println(price);
                String listId = request.getParameter("listId");
                System.out.println(listId);
                String UserId = request.getParameter("userId");
                System.out.println(UserId);
                LocalDate date = LocalDate.now();
                if (dateParam != null && !dateParam.isEmpty()) {
                     date = LocalDate.parse(dateParam);
                }
                AIEventRecommendationService.recommendEinkaufsliste(gericht,date,price,listId,UserId);

            }else{
                    String listIdParam = request.getParameter("listId");
                    String item = request.getParameter("item");

                    if (listIdParam != null && item != null && !item.isEmpty()) {
                        Long listId = Long.parseLong(listIdParam);
                        Einkaufsliste list = einkaufslisteDAO.getEinkaufsliste(listId);
                        EinkaufslisteItem newItem = new EinkaufslisteItem(list, item);
                        itemDAO.addItem(newItem);
                    }
                }

            response.sendRedirect(request.getContextPath() + "/einkaufsliste?userId=" + userId+ "&date=" + dateParam);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request: " + e.getMessage());
        }
    }

    private void doDeleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String itemIdParam = request.getParameter("itemId");
        String dateParam = request.getParameter("date");
        if (itemIdParam == null || itemIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing itemId.");
            return;
        }

        try {
            Long itemId = Long.parseLong(itemIdParam);
            itemDAO.deleteItem(itemId);

            response.sendRedirect(request.getContextPath() + "/einkaufsliste?userId=" + request.getParameter("userId")+ "&date=" + dateParam);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting item.");
        }
    }

    private void doDeleteList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String listIdParam = request.getParameter("listId");
        String dateParam = request.getParameter("date");
        if (listIdParam == null || listIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing listId.");
            return;
        }

        try {
            Long listId = Long.parseLong(listIdParam);
            einkaufslisteDAO.deleteEinkaufsliste(listId);

            response.sendRedirect(request.getContextPath() + "/einkaufsliste?userId=" + request.getParameter("userId")+ "&date=" + dateParam);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting list.");
        }
    }
}
