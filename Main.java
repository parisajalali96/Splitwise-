
/*
  Just Start the program from here and do nothing else here.
 */

import controllers.LoginMenuController;
import controllers.SignUpMenuController;
import models.User;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            String input = scanner.nextLine();

            if (input.equals("exit")) {
                break;
            }else if (input.startsWith("register")) {
                signUpMenu(input);
            }else if (input.equals("go to login menu")) {
                String login = scanner.nextLine();
                loginMenu(login);
            }
        }
    }

    public static void loginMenu (String input){
        LoginMenuController loginController = new LoginMenuController(users);
        String[] parts = input.split(" ");
        boolean valid = true;
        String username = null;
        String password = null;
        for (int i = 0; i < parts.length; i++) {
            switch (parts[i]) {
                case "-u" : {
                    username = parts[i+1];
                    if (!loginController.usernameExist(username)) {
                        System.out.println("username doesn't exist!");
                        valid = false;
                        break;
                    }
                }
                case "-p" : {
                    password = parts[i+1];
                    if(!valid) break;
                    else if(!loginController.isPasswordValid(password, username)) {
                        System.out.println("password is incorrect!");
                        valid = false;
                        break;
                    }
                }
            }
            if (valid) {
                currentUser = loginController.findUser(username, password);
                if (currentUser != null) {
                    System.out.println("user logged in successfully.you are now in dashboard!");
                }
            }
        }

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
}
