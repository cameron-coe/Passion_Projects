package main.java;

import java.time.LocalDate;

public class BudgetApplication {

    /*******************************************************************************************************************
     * Instance Variables
     */
    private Menu menu;
    private BudgetManager budgetManager;
    private BudgetItem selectedBudgetItem;
    private LocalDate startDate;
    private LocalDate endDate;


    /*******************************************************************************************************************
     * Main Method
     */
    public static void main(String[] args) {
        Menu menu = new Menu();
        BudgetManager budgetManager = new BudgetManager();
        BudgetApplication app = new BudgetApplication(menu, budgetManager);
        app.run();
    }


    /*******************************************************************************************************************
     * Constructor
     */
    public BudgetApplication (Menu menu, BudgetManager budgetManager) {
        this.menu = menu;
        this.budgetManager = budgetManager;
    }

    /*******************************************************************************************************************
     * Setters
     */
    public void setStartAndEndDatesToFirstAndLastDaysOfCurrentMonth() {
        LocalDate currentDate = LocalDate.now();
        this.startDate = currentDate.withDayOfMonth(1);
        this.endDate = currentDate.withDayOfMonth(currentDate.getMonth().length(currentDate.isLeapYear()));
    }


    /*******************************************************************************************************************
     * Runtime Method
     */
    public void run () {
        setStartAndEndDatesToFirstAndLastDaysOfCurrentMonth();

        // Create a root budget item if one doesn't exist
        if (budgetManager.getRootBudgetItem() == null) {
            budgetManager.generateNewRootBudgetItem();
            selectedBudgetItem = budgetManager.getRootBudgetItem();
        }

        menu.showWelcomeMessage();

        /** Temporary Objects for Presentation ************************************************************************/
        LocalDate firstTransactionDate = LocalDate.parse("2024-01-01");

        BudgetItem groceries = new BudgetItem("Groceries", budgetManager.getRootBudgetItem());
        groceries.setupRecurringTransactionNoEndDate(1, "W", firstTransactionDate);

        BudgetItem food = new BudgetItem("Food", groceries);

        BudgetItem milk = new BudgetItem("Milk", food);
        milk.setTransactionAmount(-4);

        BudgetItem bread = new BudgetItem("Bread", food);
        bread.setTransactionAmount(-3);

        BudgetItem candy = new BudgetItem("Candy", food);
        candy.setTransactionAmount(-0.25);
        candy.setupOneTimeTransaction(endDate);


        BudgetItem clothes = new BudgetItem("Clothes", groceries);
        clothes.setTransactionAmount(-15);
        BudgetItem miscGroceries = new BudgetItem("Misc.", groceries);
        miscGroceries.setTransactionAmount(-50);

        BudgetItem income = new BudgetItem("Income", budgetManager.getRootBudgetItem());

        BudgetItem mainJob = new BudgetItem("Primary Job", income);

        BudgetItem paycheck = new BudgetItem("Paycheck", mainJob);
        paycheck.setTransactionAmount(1200);
        paycheck.setupRecurringTransactionNoEndDate(2, "W", firstTransactionDate);

        BudgetItem bonus = new BudgetItem("Monthly Bonus", mainJob);
        bonus.setTransactionAmount(500);
        bonus.setupRecurringTransactionNoEndDate(1, "M", firstTransactionDate);

        BudgetItem test1 = new BudgetItem("Test1", budgetManager.getRootBudgetItem());
        BudgetItem test2 = new BudgetItem("Test2", test1);
        BudgetItem test3 = new BudgetItem("Test3", test2);
        BudgetItem test4 = new BudgetItem("Test4", test3);
        BudgetItem test5 = new BudgetItem("Test5", test4);
        BudgetItem test6 = new BudgetItem("Test6", test5);
        BudgetItem test7 = new BudgetItem("Test7", test6);
        test7.setTransactionAmount(0.01);
        test7.setupRecurringTransactionNoEndDate(1, "D", firstTransactionDate);


        BudgetItem miscExpenses = new BudgetItem("Mics. Expenses", budgetManager.getRootBudgetItem());
        paycheck.setupRecurringTransactionNoEndDate(24, "M", LocalDate.parse("2023-01-01"));



        /** RUNTIME LOOP **/
        boolean isRunning = true;
        while (isRunning) {
            menu.showCurrentBudgetItemPage(this.selectedBudgetItem, startDate, endDate);
            String userInput = menu.requestUserInputAndFormat();

            // Go to selected deck if we can parse user input to an int, otherwise check for other input command
            try{
                int userInputToInt = Integer.parseInt(userInput);
                if (0 < userInputToInt && userInputToInt <= selectedBudgetItem.getListOfChildBudgetItems().size()) {
                    // TODO
                    //allDecksManager.setCurrentDeck(allDecksManager.getListOfAllDecks().get( userInputToInt - 1 ));
                    //this.currentPage = DECK_PAGE;
                } else {
                    menu.askUserToRetryInput("Sorry, the given number is out of bounds.");
                }

            } catch (NumberFormatException e) {
                if (userInput.equals("a")) {
                    //TODO
                }
                else if (userInput.equals("t")) {
                    // Show Full Budget Tree for time range
                    menu.showBudgetTree(this.selectedBudgetItem, startDate, endDate);
                    menu.pressEnterToContinue();
                    menu.requestUserInput(); // stops user from going right back to the Budget Item Page

                }
                else if (userInput.equals("z")) {
                    // End Program
                    isRunning = false;
                }
                else {
                    menu.askUserToRetryInput("Sorry, the input could not be read. Please try again.");
                }
            }
        }


        //this.budgetMenu.showWelcomeScreen -- and other stuff from the other

        /* TODO
         * Show budget for any given timeframe
         * Default budget timeframes to months
         *
         * Menu:
         * List budget categories, amounts, and indent and categorize appropriately
         * Add sub-category page
         */
    }

}
