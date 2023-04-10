package com.mycompany.englishquiz;

import com.mycompany.englishquiz.Code.User;

public class UserSession {

    private static UserSession instance;
    private User user;
    private UserDAO userDAO;

    private UserSession() {
    }

    public void logIn(User user) {
        System.out.println("Logging in user: " + user.getHoTen());
        this.setUser(user);
    }

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public boolean isLoggedIn() {
        return getUser() != null;
    }

    public void logOut() {
        setUser(null);
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public int getLoggedInUserId() {
        if (isLoggedIn()) {
            return getUser().getId();
        } else {
            return -1; // or throw an exception
        }
    }

    public void logout() {
        setUser(null);
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public static void setInstance(UserSession aInstance) {
        instance = aInstance;
    }
}
