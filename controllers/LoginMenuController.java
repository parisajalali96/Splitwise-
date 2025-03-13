package controllers;
/*
Explanation:
- This is a controller class for the login menu Controller.
- This class will be used to implement functions that do log in menu operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

import models.User;

import java.util.ArrayList;

public class LoginMenuController {
    private static ArrayList<User> users = new ArrayList<User>();
    public LoginMenuController(ArrayList<User> users) {
        this.users = users;
    }

    public static boolean usernameExist (String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPasswordValid (String password, String username) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static User findUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static boolean matchEmail (String username, String email) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public static String forgotPassword (String username, String email) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getEmail().equals(email)) {
                return user.getPassword();
            }
        }
        return null;
    }
}
