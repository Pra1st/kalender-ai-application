package com.example.webanwendungenmitjavahausarbeit.service;


import com.example.webanwendungenmitjavahausarbeit.dao.UserDAO;
import com.example.webanwendungenmitjavahausarbeit.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    //register a new user
    public boolean register(String username, String email, String password) {
        if (userDAO.getUserByUsername(username) != null) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(username, email, hashedPassword);
        userDAO.registerUser(user);
        return true;
    }

    //authenticate a user
    public boolean authenticate(String username, String password, HttpServletRequest request) {
        User user = userDAO.getUserByUsername(username);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            request.getSession().setAttribute("userId", user.getId());
            return true;
        }
        return false;
    }
}