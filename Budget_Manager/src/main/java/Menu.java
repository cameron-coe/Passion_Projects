package main.java;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    /*******************************************************************************************************************
     * Constants
     */
    private final static Scanner userInput = new Scanner(System.in);
    private final static String INDENT = "  ";
    private final static String BLOCK = "■";
    private final static String BRANCH = "│";
    private final static String LEAF = "├─";
    private final static String END_LEAF = "└─";


    /*******************************************************************************************************************
     * Constructor
     */
    public Menu () {}


    /*******************************************************************************************************************
     * Welcome Method
     */
    public void showWelcomeMessage() {
        System.out.println("Welcome!");
        System.out.println();
    }


    /*******************************************************************************************************************
     * Budget Item Page
     */
    public void showCurrentBudgetItemPage(BudgetItem budgetItem, LocalDate startDate, LocalDate endDate) {
        showHierarchy(budgetItem);
        showStartAndEndDates(startDate, endDate);

        // Current Budget Item
        System.out.println(BLOCK + " " + showItemNameAndAmount(budgetItem, startDate, endDate));

        // List of all of current Budget Item's Children
        int itemizer = 1;
        for(BudgetItem child : budgetItem.getListOfChildBudgetItems()) {
            String leaf = LEAF;

            // Checks if child is the final item in budgetItem.getListOfChildBudgetItems()
            if (child.equals( budgetItem.getListOfChildBudgetItems().get( budgetItem.getListOfChildBudgetItems().size() - 1 )) ) {
                leaf = END_LEAF;
            }

            System.out.println(INDENT + leaf + " " + itemizer + ".) " + showItemNameAndAmount(child, startDate, endDate));

            itemizer += 1;
        }
        System.out.println();


        // Options
        System.out.println("Type a number to select a budget item.");
        if (budgetItem.getParentBudgetItem() != null) {
            System.out.println("\"B\" -- Back" + "\uD83D\uDEA7");
        }
        // System.out.println("\"A\" -- Add Sub-category" + "\uD83D\uDEA7");
        // System.out.println("\"R\" -- Remove Sub-category" + "\uD83D\uDEA7");
        System.out.println("\"T\" -- Get Full Tree");
        // System.out.println("\"U\" -- See Upcoming Transactions" + "\uD83D\uDEA7");
        // System.out.println("\"D\" -- Edit Dates" + "\uD83D\uDEA7");
        // System.out.println("\"N\" -- Change Name" + "\uD83D\uDEA7");
        if (budgetItem.getParentBudgetItem() != null) {
            System.out.println("\"X\" -- Delete this Category" + "\uD83D\uDEA7");
        }
        System.out.println("\"Z\" -- End Program");
    }


    /*******************************************************************************************************************
     * Give Full Budget Tree for Time Range
     * TODO: If BI list is empty and transaction amount is zero, don't show
     */
    public void showBudgetTree( BudgetItem budgetItem, LocalDate startDate, LocalDate endDate) {
        showStartAndEndDates(startDate, endDate);
        System.out.println(BLOCK + " " + showItemNameAndAmount(budgetItem, startDate, endDate));
        showBudgetTreeBranches(INDENT, budgetItem, startDate, endDate);
        System.out.println();
    }


    private void showBudgetTreeBranches(String indent, BudgetItem budgetItem, LocalDate startDate, LocalDate endDate) {
        // List of all of current Budget Item's Children
        for (BudgetItem child : budgetItem.getListOfChildBudgetItems()) {
            String leaf = LEAF;

            // Checks if child is the final item in budgetItem.getListOfChildBudgetItems()
            BudgetItem lastItem = budgetItem.getListOfChildBudgetItems().get(budgetItem.getListOfChildBudgetItems().size() - 1);
            if (child.equals(lastItem)) {
                leaf = END_LEAF;
            }

            // Print child
            System.out.println(indent + leaf + " " + showItemNameAndAmount(child, startDate, endDate));

            // recursion if there are child objects in the child's ListOfChildBudgetItems
            List<BudgetItem> childChildBudgetItems = child.getListOfChildBudgetItems();
            if(!childChildBudgetItems.isEmpty() && childChildBudgetItems != null) {
                String newIndent = indent;
                if (child.equals(lastItem)) {
                    newIndent += " ";
                } else {
                    newIndent += BRANCH;
                }
                newIndent += INDENT;

                showBudgetTreeBranches(newIndent, child, startDate, endDate);
            }

        }
    }


    // Display name and transaction amount for a budget item
    public String showItemNameAndAmount(BudgetItem budgetItem, LocalDate startDate, LocalDate endDate ) {
        String outputString = budgetItem.getName();
        outputString += ": ";

        double totalTransactionAmount = budgetItem.totalRecurringTransactionAmountBetweenDateRange(startDate, endDate).doubleValue();
        if (totalTransactionAmount < 0) {
            outputString += "-";
        } else {
            outputString += "+";
        }
        outputString += "$";

        outputString += String.format("%1.2f", Math.abs(totalTransactionAmount));

        return outputString;
    }


    // Display Dates
    public void showStartAndEndDates(LocalDate startDate, LocalDate endDate) {
        System.out.println("From: " + formatLocalDate(startDate));
        System.out.println("To: " + formatLocalDate(endDate));
    }

    public void showHierarchy(BudgetItem selectedBudgetItem) {
        String directoryPath = "" + selectedBudgetItem.getName();

        selectedBudgetItem = selectedBudgetItem.getParentBudgetItem();
        while (selectedBudgetItem != null) {
            directoryPath = "" + selectedBudgetItem.getName() + " >> " + directoryPath;
            selectedBudgetItem = selectedBudgetItem.getParentBudgetItem();
        }

        System.out.println(directoryPath);
    }


    // User Input Methods
    public String requestUserInput() {
        System.out.print(">>> ");

        String input = userInput.nextLine();  // Read the user input
        System.out.println();

        return input;
    }

    public String requestUserInputAndFormat() {
        return requestUserInput().trim().toLowerCase();
    }


    public void askUserToRetryInput(String s) {
        System.out.println(s);
        System.out.println();
    }

    public void pressEnterToContinue() {
        System.out.println("Press Enter to Continue.");
    }

    // Format dates
    public String formatLocalDate(LocalDate localDate) {
        String year = "" + localDate.getYear();
        String month = "" + localDate.getMonth();
        String day = "" + localDate.getDayOfMonth();

        return month + " " + day + ", " + year;
    }


}
