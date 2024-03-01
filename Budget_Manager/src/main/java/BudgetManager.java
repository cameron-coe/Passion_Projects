package main.java;

public class BudgetManager {

    /*******************************************************************************************************************
     * Constants
     */
    private static final BudgetItem DEFAULT_BUDGET_ITEM = new BudgetItem("My Budget", null);


    /*******************************************************************************************************************
     * Instance Variables
     */
    private BudgetItem rootBudgetItem = null;


    /*******************************************************************************************************************
     * Constructor
     */
    public BudgetManager() {}


    /*******************************************************************************************************************
     * Getters
     */
    public BudgetItem getRootBudgetItem () {
        return this.rootBudgetItem;
    }


    public void generateNewRootBudgetItem () {
        this.rootBudgetItem = DEFAULT_BUDGET_ITEM;
    }


}
