
/*
  Just Start the program from here and do nothing else here.
 */

import controllers.DashboardController;
import controllers.LoginMenuController;
import controllers.SignUpMenuController;
import models.Group;
import models.User;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Group> groups = new ArrayList<>();
    private static LoginMenuController loginController = new LoginMenuController(users);
    private static SignUpMenuController signUpController = new SignUpMenuController(users);
    private static DashboardController dashboardController = new DashboardController(groups);
    private static User currentUser = new User(null, null, null, null);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            String input = scanner.nextLine();

            if (input.equals("exit")) {
                break;
            }else if (input.startsWith("register")) {
                signUpMenu(input);
            }else if (input.equals("go to signup menu")) {
                System.out.println("you are now in signup menu!");
                String signup = scanner.nextLine();
                signUpMenu(signup);
            }else if (input.equals("go to login menu")) {
                System.out.println("you are now in login menu!");
                String login = scanner.nextLine();
                loginMenu(login);
            }else if (input.startsWith("login")) {
                loginMenu(input);
            }else if (input.startsWith("forget-password")) {
                forgotPasswordMenu(input);
            }else if (input.startsWith("create-group")) {
                createGroup(input);
            }else if (input.startsWith("add-user")) {

            }
        }
    }

    public static void addUser(String input) {
        String[] parts = input.split(" ");
        String username = null;
        String email = null;
        int groupId = 1;
        boolean valid = true;
        boolean isCreator = true;
        for (int i = 0; i < parts.length; i++) {
            switch (parts[i]) {
                case "-u":{
                    username = parts[i+1];
                    if(!loginController.usernameExist(username)){
                        System.out.println("user not found!");
                        valid = false;
                    }
                    break;
                }
                case "-e":{
                    email = parts[i+1];
                    if(!loginController.matchEmail(username,email)){
                        System.out.println("the email provided does not match the username!");
                        valid = false;
                    }
                    break;
                }
                case "-g":{
                    groupId = Integer.parseInt(parts[i+1]);
                    if(!dashboardController.existGroup(groupId)){
                        System.out.println("group not found!");
                        valid = false;
                    }
                    break;
                }
            }
        }
        User addedUser = loginController.findUser(username, email);
        ArrayList<User> currentGroupMembers = dashboardController.getGroup(groupId).getMembers();
        if(dashboardController.alreadyMember(addedUser, groupId)) {
            System.out.println("user already in the group!");
            valid = false;
        }
        if (!dashboardController.getGroup(groupId).getCreator().equals(currentUser)) {
            System.out.println("only the group creator can add users!");
            valid = false;
        }

        if(valid){
            currentGroupMembers.add(addedUser);
            System.out.println("user added to the group successfully!");
        }

    }
    public static void createGroup(String input) {
        String[] parts = input.split(" ");
        String groupName = null;
        String groupType = null;
        boolean valid = true;
        for (int i = 0; i < parts.length; i++) {
            switch (parts[i]) {
                case "-n": {
                    groupName = parts[i + 1];
                    if(!dashboardController.isGroupNameValid(groupName)) {
                        System.out.println("group name format is invalid!");
                        valid = false;
                    }
                    break;
                }
                case "-t": {
                    groupType = parts[i + 1];
                    if(!dashboardController.isGroupTypeValid(groupType)) {
                        System.out.println("group type is invalid!");
                        valid = false;
                    }
                    break;
                }
            }
        }
        if (valid) {
            Group newGroup = new Group(groupName, groupType,currentUser.getUsername());
            groups.add(newGroup);
            System.out.println("group created successfully!");
        }
    }
    public static void forgotPasswordMenu(String input) {
        String[] parts = input.split(" ");
        String username = null;
        String password = null;
        String email = null;
        boolean valid = true;
        for (int i = 0; i < parts.length; i++) {
            switch (parts[i]) {
                case "-u":{
                    username = parts[i+1];
                    if(!loginController.usernameExist(username)) {
                        System.out.println("username doesn't exist!");
                        valid = false;
                    }
                    break;
                }
                case "-e":{
                    email = parts[i+1];
                    if(!loginController.matchEmail(username, email)) {
                        System.out.println("email doesn't match!");
                        valid = false;
                    }
                    break;
                }
            }
        }
        if (valid) {
            password = loginController.forgotPassword(username, email);
            System.out.println("password :" + password);
        }
    }
    public static void loginMenu (String input){
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
                    if(!loginController.isPasswordValid(password, username)) {
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
