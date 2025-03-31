package com.example.webanwendungenmitjavahausarbeit.controller;

import com.example.webanwendungenmitjavahausarbeit.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet({"/login", "/register", "/logout"})
public class UserController extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        this.userService = new UserService();
    }

    //get on login, register nad logout pages
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        if (path.equals("/login")) {
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } else if (path.equals("/register")) {
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        } else if (path.equals("/logout")) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            request.getRequestDispatcher("/WEB-INF/logout.jsp").forward(request, response);
        }
    }

    // handles login, register and logout requests
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        if (path.equals("/login")) {

            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (userService.authenticate(username, password,request)) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);

                request.getRequestDispatcher("/WEB-INF/calendar.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Username or password wrong");
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
        } else if (path.equals("/register")) {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");

            if (!password.equals(confirmPassword)) {
                request.setAttribute("error", "Passwords do not match.");
                request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
                return;
            }

            boolean success = userService.register(username, email, password);
            if (success) {
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "User already exists.");
                request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
            }
        }
    }
}