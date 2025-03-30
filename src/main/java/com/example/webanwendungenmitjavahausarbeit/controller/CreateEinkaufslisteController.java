package com.example.webanwendungenmitjavahausarbeit.controller;

import com.example.webanwendungenmitjavahausarbeit.dao.EinkaufslisteDAO;
import com.example.webanwendungenmitjavahausarbeit.model.Einkaufsliste;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/createEinkaufsliste")
public class CreateEinkaufslisteController extends HttpServlet {

    private EinkaufslisteDAO einkaufslisteDAO = new EinkaufslisteDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String userIdParam = request.getParameter("userId");
        String dateParam = request.getParameter("date");

        if (title == null || title.isEmpty() || userIdParam == null || userIdParam.isEmpty() || dateParam == null || dateParam.isEmpty()) {
            request.setAttribute("error", "Titel, Benutzer-ID oder Datum fehlt.");
            request.getRequestDispatcher("/WEB-INF/einkaufsliste.jsp").forward(request, response);
            return;
        }

        try {
            Long userId = Long.parseLong(userIdParam);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate createdDate = LocalDate.parse(dateParam, formatter);

            Einkaufsliste einkaufsliste = new Einkaufsliste(userId, createdDate, title);
            einkaufslisteDAO.addEinkaufsliste(einkaufsliste);

            response.sendRedirect(request.getContextPath() + "/einkaufsliste?userId=" + userId + "&date=" + dateParam);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Ungültige Benutzer-ID.");
            request.getRequestDispatcher("/WEB-INF/einkaufsliste.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Fehler beim Erstellen der Einkaufsliste.");
            request.getRequestDispatcher("/WEB-INF/einkaufsliste.jsp").forward(request, response);
        }
    }

}
