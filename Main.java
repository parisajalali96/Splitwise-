
/*
  Just Start the program from here and do nothing else here.
 */

import controllers.DashboardController;
import controllers.LoginMenuController;
import controllers.ProfileMenuController;
import controllers.SignUpMenuController;
import models.Expense;
import models.Group;
import models.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Main {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Group> groups = new ArrayList<>();
    private static LoginMenuController loginController = new LoginMenuController(users);
    private static SignUpMenuController signUpController = new SignUpMenuController(users);
    private static DashboardController dashboardController = new DashboardController(groups);
    private static ProfileMenuController profileController = new ProfileMenuController();
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = new User(null, null, null, null);

    public static void main(String[] args) {

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
                addUser(input);
            }else if (input.equals("show my groups")) {
                dashboardController.showGroups(currentUser);
            }else if (input.startsWith("add-expense")) {
                addExpense(input);
            }else if(input.equals("go to profile menu")) {
                String profile = scanner.nextLine();
                System.out.println("you are now in profile menu!");
                profileMenu(profile);
            }else if(input.equals("logout")) {
                logOut();
                continue;
            }else if(input.equals("show user info")) {
                profileController.showUserInfo(currentUser);
            }else if(input.startsWith("change-currency")) {
                changeCurrency(input);
            }else if(input.startsWith("change-username")) {
                changeUsername(input);
            }else if(input.equals("change-password")) {
                changePassword(input);
            }else if(input.equals("back")) {
                System.out.println("you are now in dashboard!");
            }else {
                System.out.println("invalid command!");
            }
        }
    }

    public static void changePassword(String input) {
        String[] parts = input.split(" ");
        String newPassword = null;
        String oldPassword = null;
        boolean valid = true;
        for (int i = 0; i < parts.length; i++) {
            switch (parts[i]) {
                case "-o": {
                    oldPassword = parts[i + 1];
                    if(!profileController.checkPassword(oldPassword, currentUser)) {
                        System.out.println("password incorrect!");
                        valid = false;
                    }
                    break;
                }
                case "-n": {
                    newPassword = parts[i + 1];
                    if(profileController.isPasswordNew(newPassword, currentUser)) {
                        System.out.println("please enter a new password!");
                        valid = false;
                    }
                    if(!signUpController.isPasswordValid(newPassword)) {
                        System.out.println("new password format is invalid!");
                        valid = false;
                    }
                    break;
                }
            }
        }
        if(valid) {
            profileController.changePassword(newPassword, currentUser);
        }
        System.out.println("your password changed successfully!");
    }

    public static void changeUsername(String input) {
        String[] parts = input.split(" ");
        String username = null;
        boolean valid = true;
        for (int i = 0; i < parts.length; i++) {
            switch (parts[i]) {
                case "-n": {
                    username = parts[i + 1];
                    if(!signUpController.isUsernameValid(username)) {
                        System.out.println("new username format is invalid!");
                        valid = false;
                    }
                    if(profileController.checkUsername(currentUser, username)) {
                        System.out.println("please enter a new username!");
                        valid = false;
                    }
                    if(loginController.usernameExist(username)) {
                        System.out.println("this username is already taken!");
                        valid = false;
                    }
                    break;
                }
            }
        }
        if(valid) {
            profileController.changeUsername(username, currentUser);
        }
        System.out.println("your username changed to " + username + " successfully!");
    }

    public static void changeCurrency(String input) {
        String[] parts = input.split(" ");
        String currency = null;
        boolean valid = true;
        for (int i = 0; i < parts.length; i++) {
            switch (parts[i]) {
                case "-n": {
                    currency = parts[i + 1];
                    if(!profileController.isCurrencyValid(currency)) {
                        System.out.println("currency format is invalid!");
                        valid = false;
                    }
                    break;
                }
            }
        }
        if(valid) {
            profileController.changeCurrency(currency, currentUser);
        }
        System.out.println("your currency changed to " + currency + " successfully!");
    }

    public static void logOut(){
        currentUser = new User(null, null, null, null);
        System.out.println("user logged out successfully.you are now in login menu!");
    }
    public static void profileMenu(String input) {

    }
    public static void addExpense(String input) {
        String[] parts = input.split(" ");
        int groupId = 1;
        boolean equal = true;
        int totalExpense = 0;
        int userNum = 0;
        boolean valid = true;

        for (int i = 0; i < parts.length; i++) {
            switch (parts[i]) {
                case "-g": {
                    groupId = Integer.parseInt(parts[i + 1]);
                    if(!dashboardController.existGroup(groupId)) {
                        System.out.println("group not found!");
                        valid = false;
                    }
                    break;
                }
                case "-s": {
                    if(parts[i+1].equals("equally")) equal = true;
                    else equal = false;
                    break;
                }
                case "-t": {
                    String expense = parts[i+1];
                    if(dashboardController.isExpenceValid(expense)) totalExpense += Integer.parseInt(expense);
                    else {
                        System.out.println("expense format is invalid!");
                        valid = false;
                    }
                    break;
                }
                case "-n": {
                    userNum = Integer.parseInt(parts[i+1]);
                }
            }
        }
        Group currentGroup = dashboardController.getGroup(groupId);
        if(equal) {
            ArrayList<User> users = new ArrayList<>();
            for (int i = 0; i < userNum; i++) {
                String user = scanner.nextLine();
                if(currentGroup.getMembers().contains(user)) {
                    users.add(currentUser);
                } else {
                    System.out.println(user + " not in group!");
                    valid = false;
                }
            }
        }else {
            HashMap<User, Expense> users = new HashMap<>();
            for (int i = 0; i < userNum; i++) {
                String user = scanner.next();
                User currentUser;
                Expense currentExpense;
                int expenseNum = 0;
                if(currentGroup.getMembers().contains(user)) {
                    String expense = scanner.next();
                    if(dashboardController.isExpenceValid(expense)) {
                        expenseNum = Integer.parseInt(expense);
                    }else{
                        System.out.println("expense format is invalid!");
                        valid = false;
                    }
                }else{
                    System.out.println(user + " not in group!");
                    valid = false;
                }
                if(valid){
                    currentUser = loginController.getUser(user);
                    currentExpense = new Expense(expenseNum, currentUser);
                    users.put(currentUser, currentExpense);
                }
                int sum = 0;
                for(Expense expence : users.values()) {
                    sum += expence.getExpense();
                }
                if(sum != totalExpense) {
                    System.out.println("the sum of individual costs does not equal the total cost!");
                    valid = false;
                }
            }
        }
        if(valid) {
            System.out.println("expense added successfully!");
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
            Group newGroup = new Group(groupName, groupType,currentUser);
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
                    }
                    break;
                }
                case "-p" : {
                    password = parts[i+1];
                    if(!valid) break;
                    if(!loginController.isPasswordValid(password, username)) {
                        System.out.println("password is incorrect!");
                        valid = false;
                    }
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

    public static void signUpMenu (String input) {
        String[] parts = input.split(" ");
        String username = null, password = null, email = null, name = null;
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
