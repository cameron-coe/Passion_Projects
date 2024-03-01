package main.java;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.*;

public class BudgetItem {

    /*******************************************************************************************************************
     * Constants
     */
    private static final String DAY_UNITS = "days";
    private static final String WEEK_UNITS = "weeks";
    private static final String MONTH_UNITS = "months";


    /*******************************************************************************************************************
     * Instance Variables
     */
    private String name;
    private BudgetItem parentBudgetItem;
    private List<BudgetItem> listOfChildBudgetItems;  // sub-categories of this budget category
    private BigDecimal transactionAmount;  // The transaction amount if there are no sub-categories in this budget category
    private LocalDate dateOfFirstTransaction;
    private LocalDate dateOfLastTransaction;

    // The following 2 variables are saying that the transaction will
    // occur every <recurringPaymentIntervalTimeUnit> <recurringPaymentIntervalNumber>
    // Example: the transaction will happen every 2 WEEKS
    private String recurringPaymentIntervalTimeUnit = "";
    private int recurringPaymentIntervalNumber = 1;


    /*******************************************************************************************************************
     * Constructors
     */
    public BudgetItem() {
        // This budget category will act as a single transaction when this list is null or empty
        this.listOfChildBudgetItems = new ArrayList<BudgetItem>();
        this.setTransactionAmount(0.00);
        this.parentBudgetItem = null;
        setupOneTimeTransaction( LocalDate.now() );
    }

    public BudgetItem(String name, BudgetItem parent) {
        // This budget category will act as a single transaction when this list is null or empty
        this.listOfChildBudgetItems = new ArrayList<BudgetItem>();
        this.setTransactionAmount(0.00);

        this.name = name;

        if (parent != null) {
            this.setParent(parent); // Method manages hierarchy
            this.dateOfFirstTransaction = parent.getDateOfFirstTransaction();
            this.dateOfLastTransaction = parent.getDateOfLastTransaction();
            this.recurringPaymentIntervalTimeUnit = parent.getRecurringPaymentIntervalTimeUnit();
            this.recurringPaymentIntervalNumber = parent.getRecurringPaymentIntervalNumber();
        }
    }


    /*******************************************************************************************************************
     * Setters
     */
    public void setName(String name) {
        this.name = name;
    }

    // Only sets transaction amount if the budget item has no children
    public void setTransactionAmount(double transactionAmount) {
        if (listOfChildBudgetItems == null || listOfChildBudgetItems.size() == 0) {
            this.transactionAmount = BigDecimal.valueOf(transactionAmount);
        }
    }


    /*******************************************************************************************************************
     * Getters
     */
    public String getName() {
        return this.name;
    }

    public BudgetItem getParentBudgetItem() {
        return this.parentBudgetItem;
    }

    public List<BudgetItem> getListOfChildBudgetItems() {
        return this.listOfChildBudgetItems;
    }

    public LocalDate getDateOfFirstTransaction() {
        return this.dateOfFirstTransaction;
    }

    public LocalDate getDateOfLastTransaction() {
        return this.dateOfLastTransaction;
    }

    public String getRecurringPaymentIntervalTimeUnit() {
        return this.recurringPaymentIntervalTimeUnit;
    }

    public int getRecurringPaymentIntervalNumber() {
        return this.recurringPaymentIntervalNumber;
    }

    public String getDayUnit() {
        return DAY_UNITS;
    }

    public String getWeekUnit() {
        return WEEK_UNITS;
    }

    public String getMonthUnit() {
        return MONTH_UNITS;
    }


    /*******************************************************************************************************************
     * Sets Transaction Dates
     */
    public void setDateOfFirstTransaction(LocalDate newDateOfFirstTransaction) {
        // The first transaction date cannot be set to null
        if (newDateOfFirstTransaction == null) { return; }

        // First transaction date cannot be after the last transaction date
        if (newDateOfFirstTransaction.isAfter(this.dateOfLastTransaction)) {
            this.dateOfFirstTransaction = this.dateOfLastTransaction;
        } else {
            this.dateOfFirstTransaction = dateOfFirstTransaction;
        }
    }

    public void setDateOfLastTransaction(LocalDate newDateOfLastTransaction) {
        if (newDateOfLastTransaction == null) {
            // Last transaction date CAN be null if last transaction date is undetermined
            this.dateOfLastTransaction = null;
        } else if (newDateOfLastTransaction.isBefore(this.dateOfFirstTransaction)) {
            // Last transaction date cannot be before the first transaction date
            this.dateOfLastTransaction = this.dateOfFirstTransaction;
        } else {
            this.dateOfLastTransaction = dateOfLastTransaction;
        }
    }


    /*******************************************************************************************************************
     * Sets Up recurring transactions and one-time transactions
     */
    public void setupOneTimeTransaction(double transactionAmount, LocalDate dateOfTransaction) {
        this.setTransactionAmount(transactionAmount);
        this.setupOneTimeTransaction (dateOfTransaction);
    }

    public void setupOneTimeTransaction(LocalDate dateOfTransaction) {
            this.dateOfFirstTransaction = dateOfTransaction;
            this.dateOfLastTransaction = dateOfTransaction;

            // Resetting recurring payment variables
            this.recurringPaymentIntervalNumber = 1;
            this.recurringPaymentIntervalTimeUnit = "";
    }

    // Sets up recurring payments WITH a transaction amount unless this budget item has children
    public void setupRecurringTransactions (double transactionAmount,
                                        int recurringPaymentIntervalNumber,
                                        String recurringPaymentIntervalTimeUnit,
                                        LocalDate dateOfFirstTransaction,
                                        LocalDate dateOfLastTransaction) {
        this.setTransactionAmount(transactionAmount);
        this.setupRecurringTransactions (recurringPaymentIntervalNumber,
                recurringPaymentIntervalTimeUnit,
                dateOfFirstTransaction,
                dateOfLastTransaction);
    }

    // Sets up recurring payments WITHOUT a transaction amount unless this budget item has children
    public void setupRecurringTransactions (int recurringPaymentIntervalNumber,
                                        String recurringPaymentIntervalTimeUnit,
                                        LocalDate dateOfFirstTransaction,
                                        LocalDate dateOfLastTransaction) {
        if (this.listOfChildBudgetItems == null || this.listOfChildBudgetItems.size() == 0) {
            this.recurringPaymentIntervalNumber = recurringPaymentIntervalNumber;
            this.dateOfFirstTransaction = dateOfFirstTransaction;
            this.dateOfLastTransaction = dateOfLastTransaction;

            if (recurringPaymentIntervalTimeUnit.equalsIgnoreCase("D")) {
                this.recurringPaymentIntervalTimeUnit = DAY_UNITS;
            } else if (recurringPaymentIntervalTimeUnit.equalsIgnoreCase("W")) {
                this.recurringPaymentIntervalTimeUnit = WEEK_UNITS;
            } else if (recurringPaymentIntervalTimeUnit.equalsIgnoreCase("M")) {
                this.recurringPaymentIntervalTimeUnit = MONTH_UNITS;
            }
        }
    }

    public void setupRecurringTransactionNoEndDate (int recurringPaymentIntervalNumber,
                                                     String recurringPaymentIntervalTimeUnit,
                                                     LocalDate dateOfFirstTransaction) {

        this.setupRecurringTransactions(recurringPaymentIntervalNumber,
                recurringPaymentIntervalTimeUnit,
                dateOfFirstTransaction,
                null);
    }

    /*******************************************************************************************************************
     * Managing Inheritance
     */

    // These methods are private because they do not manage hierarchy.
    private void setParentBudgetItemNoRelationshipManagement (BudgetItem parentBudgetItem) {
        this.parentBudgetItem = parentBudgetItem;
    }
    private void addChildBudgetItemNoRelationshipManagement (BudgetItem childBudgetItem) {
        this.listOfChildBudgetItems.add(childBudgetItem);
    }

    // These are the public methods that manage hierarchy
    public void setParent(BudgetItem parentBudgetItem) {
        setupParentChildRelationship(parentBudgetItem, this);
    }
    public void addChild(BudgetItem budgetItemChildToAdd) {
        setupParentChildRelationship(this, budgetItemChildToAdd);
    }

    private void setupParentChildRelationship(BudgetItem parent, BudgetItem child) {
        // Cannot add the same child object under the parent twice
        if(parent.getListOfChildBudgetItems().contains(child)) { return; }

        // The child cannot be the same object as the parent or one of parent's ancestors
        BudgetItem ancestor = parent;
        while (ancestor != null) {
            if (ancestor.equals(child)) { return; }

            ancestor = ancestor.getParentBudgetItem();
        }

        // If child already has a parent assigned to it, remove it from the old parent's list of children
        if (child.getParentBudgetItem() != null) {
            BudgetItem oldParent = child.getParentBudgetItem();
            oldParent.getListOfChildBudgetItems().remove(child);
        }

        child.setParentBudgetItemNoRelationshipManagement(parent);
        parent.addChildBudgetItemNoRelationshipManagement(child);

        // Parent Budget item shouldn't have its own transaction amount if it's a broader category for other budget items
        // Set last transaction date to first transaction date
        parent.setTransactionAmount(0.00);
    }


    /*******************************************************************************************************************
     * Returns either the transaction amount of this budget item,
     * or the sum of all child budget item transactions if this budget item has child budget items
     */
    public BigDecimal getTotalTransactionAmount() {
        if (listOfChildBudgetItems == null || listOfChildBudgetItems.size() == 0) {
            return this.transactionAmount;
        } else {
            BigDecimal totalTransactionAmount = BigDecimal.ZERO;
            for (BudgetItem currentBudgetItem : this.listOfChildBudgetItems) {
                totalTransactionAmount = totalTransactionAmount.add( currentBudgetItem.getTotalTransactionAmount() );
            }
            return  totalTransactionAmount;
        }
    }


    /*******************************************************************************************************************
     * Gets the LocalDate of a recurring transaction at an interval after the first transaction date
     * Date of first transaction starts at 0
     * returns null if not a recurring transaction
     * Allows Negative inputs
     */
    public LocalDate getDateOfRecurringTransactionsWithInterval(int interval) {
        LocalDate outputDateOfRecurringPayment = this.dateOfFirstTransaction;
        int timeUnitsToAdd = this.recurringPaymentIntervalNumber * interval;

        if (this.recurringPaymentIntervalTimeUnit.equals(DAY_UNITS)) {
            outputDateOfRecurringPayment = outputDateOfRecurringPayment.plusDays(timeUnitsToAdd);
        } else if (this.recurringPaymentIntervalTimeUnit.equals(WEEK_UNITS)) {
            outputDateOfRecurringPayment = outputDateOfRecurringPayment.plusWeeks(timeUnitsToAdd);
        } else if (this.recurringPaymentIntervalTimeUnit.equals(MONTH_UNITS)) {
            outputDateOfRecurringPayment = outputDateOfRecurringPayment.plusMonths(timeUnitsToAdd);
        }

        return outputDateOfRecurringPayment;
    }


    /*******************************************************************************************************************
     * Returns a list of recurring transaction dates that occur within a date range
     * This range includes the starting date and the end date
     */
    public List<LocalDate> recurringTransactionDatesInDateRangeForThisBudgetItem(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> outputListOfDates = new ArrayList<>();

        // End method if startDate or endDate are null
        if (startDate == null || endDate == null) {
            return outputListOfDates;
        }

        // End method if dateOfFirstTransaction is null
        if (this.dateOfFirstTransaction == null) {
            return outputListOfDates;
        }

        // Don't run if recurringPaymentIntervalNumber <= Zero --> prevents infinite loops and dividing by zero
        if(this.recurringPaymentIntervalNumber <= 0) {
            return outputListOfDates;
        }

        int interval = 0;

        // Sets the value the interval will start at
        if (this.recurringPaymentIntervalTimeUnit.equals(DAY_UNITS)) {
            int daysBetweenFirstTransactionAndStartDate = (int) (DAYS.between(this.dateOfFirstTransaction, startDate));
            interval = daysBetweenFirstTransactionAndStartDate / recurringPaymentIntervalNumber;
        } else if (this.recurringPaymentIntervalTimeUnit.equals(WEEK_UNITS)) {
            int weeksBetweenFirstTransactionAndStartDate = (int) (WEEKS.between(this.dateOfFirstTransaction, startDate));
            interval = weeksBetweenFirstTransactionAndStartDate / recurringPaymentIntervalNumber;
        } else if (this.recurringPaymentIntervalTimeUnit.equals(MONTH_UNITS)) {
            int monthsBetweenFirstTransactionAndStartDate = (int) (MONTHS.between(this.dateOfFirstTransaction, startDate));
            interval = monthsBetweenFirstTransactionAndStartDate / recurringPaymentIntervalNumber;
        }

        // resets interval if startDate comes before this.dateOfFirstTransaction
        if (interval < 0) {
            interval = 0;
        }

        LocalDate dateToCheck = getDateOfRecurringTransactionsWithInterval(interval);

        // Check recurring dates if they fall before or are the same as the endDate
        while (dateToCheck.isBefore(endDate) || dateToCheck.isEqual(endDate)) {

            // Check if the recurring date is after or equal to the startDate
            if (dateToCheck.isAfter(startDate) || dateToCheck.isEqual(startDate)) {

                // Adds the date to check to the output list if the date is equal to or
                // between 'this.dateOfFirstTransaction' and 'this.dateOfLastTransaction'.

                if (dateToCheck.isAfter(this.dateOfFirstTransaction) || dateToCheck.isEqual(this.dateOfFirstTransaction)) {
                    if (this.dateOfLastTransaction == null) {
                        // A null dateOfLastTransaction means there's no end date and should be included
                        outputListOfDates.add(getDateOfRecurringTransactionsWithInterval(interval));
                    }
                    else if (dateToCheck.isBefore(this.dateOfLastTransaction) || dateToCheck.isEqual(this.dateOfLastTransaction)) {
                        outputListOfDates.add(getDateOfRecurringTransactionsWithInterval(interval));
                    }
                }
            }

            // End the loop if the updated date is the same as the date to check, which means it's an infinite loop
            interval += 1;
            LocalDate updatedDateToCheck = getDateOfRecurringTransactionsWithInterval(interval);
            if (dateToCheck.equals(updatedDateToCheck)) {
                break;
            } else {
                dateToCheck = updatedDateToCheck;  // Update the date to check
            }

            // End loop if the date to check is after the date of last transaction, since
            // there's no need to loop further.
            // Having no last transaction date means keep going until the end date
            if (this.dateOfLastTransaction != null) {
                if (dateToCheck.isAfter(this.dateOfLastTransaction)) {
                    break;
                }
            }
        }

        return outputListOfDates;
    }


    /*******************************************************************************************************************
     * Gets the total transaction amount of Budget item and child budget items in a given timeframe
     */
    public BigDecimal totalRecurringTransactionAmountBetweenDateRange(LocalDate startDate, LocalDate endDate) {
        BigDecimal totalTransactionAmount = BigDecimal.ZERO;

        if (listOfChildBudgetItems == null || listOfChildBudgetItems.size() == 0) {
            int numberOfTransactionsBetweenDates =
                    recurringTransactionDatesInDateRangeForThisBudgetItem(startDate, endDate).size();
            BigDecimal numberOfTransactionsBetweenDatesAsBigDecimal = BigDecimal.valueOf(numberOfTransactionsBetweenDates);

            totalTransactionAmount =
                    totalTransactionAmount.add(this.transactionAmount.multiply(numberOfTransactionsBetweenDatesAsBigDecimal));

        } else {
            for (BudgetItem currentBudgetItem : this.listOfChildBudgetItems) {
                totalTransactionAmount =
                        totalTransactionAmount.add(currentBudgetItem.totalRecurringTransactionAmountBetweenDateRange(startDate, endDate));
            }
        }

        return totalTransactionAmount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BudgetItem)) return false;
        BudgetItem that = (BudgetItem) o;
        return getRecurringPaymentIntervalNumber() == that.getRecurringPaymentIntervalNumber() && Objects.equals(getName(), that.getName()) && Objects.equals(getParentBudgetItem(), that.getParentBudgetItem()) && Objects.equals(getListOfChildBudgetItems(), that.getListOfChildBudgetItems()) && Objects.equals(transactionAmount, that.transactionAmount) && Objects.equals(getDateOfFirstTransaction(), that.getDateOfFirstTransaction()) && Objects.equals(getDateOfLastTransaction(), that.getDateOfLastTransaction()) && Objects.equals(getRecurringPaymentIntervalTimeUnit(), that.getRecurringPaymentIntervalTimeUnit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getParentBudgetItem(), getListOfChildBudgetItems(), transactionAmount, getDateOfFirstTransaction(), getDateOfLastTransaction(), getRecurringPaymentIntervalTimeUnit(), getRecurringPaymentIntervalNumber());
    }
}
