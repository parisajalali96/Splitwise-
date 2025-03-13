package controllers;
/*
Explanation:
- This is a controller class for the sign-up menu Controller.
- This class will be used to implement functions that do sign up menu operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

import models.User;

import java.util.ArrayList;

public class SignUpMenuController {
    private static ArrayList<User> users = new ArrayList<>();
    public SignUpMenuController(ArrayList<User> users) {
        this.users = users;
    }

    public static boolean isUsernameValid (String username) {
        String regex = "^[a-zA-Z][a-zA-Z0-9._-]{2,8}[a-zA-Z]$";
        return username.matches(regex);
    }

    public static boolean isUsernameUnique(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }
    public static boolean isPasswordValid (String password) {
       String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{6,12}";
       return password.matches(regex);
    }

    public static boolean isEmailDomainValid(String domain) {
        String regex = "^[a-z](?:[a-z]|[-_.](?=[a-z])){1,5}[a-z]$";
        return domain.matches(regex);
    }

    public static boolean isEmailTLDValid(String TLD) {
        if (TLD.equals("edu") || TLD.equals("org") || TLD.equals("net") || TLD.equals("com")) return true;
        return false;
    }

    public static boolean isNameValid (String name) {
        String regex = "^[A-Za-z][A-Za-z-]*$";
        return name.matches(regex);
    }
}
