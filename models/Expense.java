package models;
/*
Explanation:
- when we create an expense, we need to store some information about it.
- Expense is something that we need to make it an object.
- put those information here and use them in your code.
 */

public class Expense{
    private int expense;
    private User inDebt;
    private User outDebt;
    private int groupId;


    public Expense (int expense, User outDebt, User inDebt, int groupId){
        this.expense = expense;
        this.outDebt = outDebt;
        this.inDebt = inDebt;
        this.groupId = groupId;
    }

    public int getExpense(){
        return expense;
    }

    public User getUserInDebt(){
        return inDebt;
    }
    public User getUserOutDebt(){
        return outDebt;
    }
    public int getGroupId(){
        return groupId;
    }

}
