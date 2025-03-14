package controllers;
/*
Explanation:
- This is a controller class for the profile menu Controller.
- This class will be used to implement functions that do profile menu operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

import models.User;

public class ProfileMenuController {

    public static void showUserInfo(User user) {
        System.out.println("username : " + user.getUsername());
        System.out.println("password : " + user.getPassword());
        System.out.println("currency : " );
        System.out.println("email : " + user.getEmail());
        System.out.println("name : " + user.getName());
    }

    public static boolean isCurrencyValid (String currency) {
        if(currency.equals("GTC") || currency.equals("SUD") || currency.equals("QTR")) {
            return true;
        }
        return false;
    }
    public static void changeCurrency(String currency, User user) {
        user.setCurrency(currency);
    }
    public static void changeUsername(String username, User user) {
        user.setUsername(username);
    }
    public static void changePassword(String password, User user) {
        user.setUsername(password);
    }
    public static boolean checkUsername (User user, String username) {
        if(username.equals(user.getUsername())) {
            return true;
        }
        return false;
    }
    public static boolean checkPassword (String password, User user) {
        if(password.equals(user.getPassword())) {
            return true;
        }
        return false;
    }
    public static boolean isPasswordNew (String password, User user) {
        if(password.equals(user.getPassword())) {
            return true;
        }
        return false;
    }
}
