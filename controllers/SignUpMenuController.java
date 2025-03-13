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

    public static void signUpMenu (String input) {
        SignUpMenuController signUpController = new SignUpMenuController(users);
        String[] parts = input.split(" ");
        String username = null;
        String password = null;
        String email = null;
        String name = null;
        boolean valid = true;
        for (int i = 0; i < parts.length; i++) {
            switch (parts[i]) {
                case "-u" : {
                    username = parts[i+1];
                    if(!signUpController.isUsernameValid(username)) {
                        System.out.println("username format is invalid!");
                        valid = false;
                    }
                    if(!signUpController.isUsernameUnique(username)) {
                        System.out.println("this username is already taken!");
                        valid = false;
                    }
                    break;
                }
                case "-p" : {
                    password = parts[i+1];
                    if(!signUpController.isPasswordValid(password)) {
                        System.out.println("password format is invalid!");
                        valid = false;
                    }
                    break;
                }
                case "-e" : {
                    email = parts[i+1];
                    String [] emailParts = email.split("@");
                    String emailUser = emailParts[0];

                    String domainPart = emailParts[1];
                    int dotIndex = domainPart.indexOf('.');
                    String domain = domainPart.substring(0, dotIndex);
                    String TLD = domainPart.substring(dotIndex+1);
                    if (!signUpController.isUsernameValid(emailUser) ||
                            !signUpController.isEmailDomainValid(domain) ||
                            !signUpController.isEmailTLDValid(TLD)) {
                        System.out.println("email format is invalid!");
                        valid = false;
                    }
                    break;
                }
                case "-n" : {
                    name = parts[i+1];
                    if(!signUpController.isNameValid(name)) {
                        System.out.println("name format is invalid!");
                        valid = false;
                    }
                    break;
                }
            }
        }
        if (valid) {
            User user = new User(username, password, email, name);
            users.add(user);
            System.out.println("user registered successfully.you are now in login menu!");
        }
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
