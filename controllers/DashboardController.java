package controllers;

import models.Group;
import models.User;

import java.util.ArrayList;

/*
Explanation:
- This is a controller class for the dashboard Controller.
- This class will be used to implement functions that do dashboard operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */
public class DashboardController {
    private static ArrayList<Group> groups = new ArrayList<>();
    public DashboardController(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public static boolean isGroupNameValid(String name) {
        String regex = "^[a-zA-Z0-9!@#$%^&*]{4,30}$";
        return name.matches(regex);
    }

    public static boolean isGroupTypeValid(String type) {
        if(type.equals("Home") || type.equals("Trip") || type.equals("Zan-O-Bache") || type.equals("Other")) return true;
        return false;
    }

    public static boolean existGroup (int id) {
        for(Group group : groups) {
            if(group.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public static boolean alreadyMember (User user, int id) {
        Group currentGroup = null;
        for(Group group : groups) {
            if(group.getId() == id) {
                currentGroup = group;
            }
        }
        ArrayList<User>members = currentGroup.getMembers();
        if(members.contains(user)) return true;
        return false;
    }

    public static Group getGroup (int id) {
        for(Group group : groups) {
            if(group.getId() == id) {
                return group;
            }
        }
        return null;
    }

    public static void showGroups (User user) {
        for(Group group : groups) {
            if(group.getMembers().contains(user)) {
                groupPrint(group);
            }
        }
    }

    public static void groupPrint (Group group) {
        System.out.println("group name : " + group.getName());
        System.out.println("group id : " + group.getId());
        System.out.println("type : " + group.getType());
        System.out.println("creator : " + group.getCreator().getName());
        System.out.println("members : ");
        for (User user : group.getMembers()) {
            System.out.println(user.getName());
        }
        System.out.println("--------------------");
    }

}