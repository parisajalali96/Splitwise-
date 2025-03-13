package models;
/*
Explanation:
- In our app, we have groups that have some information.
- Group is something that we need to make it an object because it looks like an object (:
- put those information here and use them in your code.
 */

import java.util.ArrayList;

public class Group {
    private String name;
    private String type;
    private static int id = 1;
    private User creator;
    private ArrayList<User> members = new ArrayList<>();

    public Group(String name, String type, User creator) {
        this.name = name;
        this.type = type;
        this.creator = creator;
        members.add(creator);
        id = ++Group.id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public User getCreator() {
        return creator;
    }

}
