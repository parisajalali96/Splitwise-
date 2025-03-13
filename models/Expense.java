package models;
/*
Explanation:
- when we create an expense, we need to store some information about it.
- Expense is something that we need to make it an object.
- put those information here and use them in your code.
 */

public class Expense{
    private int expense;
    private User user;

    public Expense (int expense, User user){
        this.expense = expense;
        this.user = user;
    }

    public int getExpense(){
        return expense;
    }

    public User getUser(){
        return user;
    }

}
