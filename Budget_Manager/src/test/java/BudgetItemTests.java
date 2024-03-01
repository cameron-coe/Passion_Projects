package test.java;

import main.java.BudgetItem;
import org.junit.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BudgetItemTests {

    private BudgetItem targetParent = new BudgetItem();
    BudgetItem targetChild1 = new BudgetItem();
    BudgetItem targetChild2 = new BudgetItem();

    @Before
    public void before_tests() {
        targetParent.setName("All Transactions");
        targetParent.setDateOfFirstTransaction(LocalDate.parse("2024-02-20"));

        targetChild1.setName("Groceries");
        targetParent.addChild(targetChild1);

        targetChild2.setName("Doctor Visit");
        targetParent.addChild(targetChild2);
    }


    private void setupFullTestBudget() {
        //targetChild1.setupRecurringPayments();
        //BudgetItem targetGrandchild1()
        // targetChild2 charges $10 every 5 days
        // have transactions and expenses
    }


    /*******************************************************************************************************************
     * Testing that hierarchy and inheritance work as intended
     */
    @Test
    public void parent_contains_child_budget_item_objects() {
        List<BudgetItem> expectedOutput = new ArrayList<>();
        expectedOutput.add(targetChild1);
        expectedOutput.add(targetChild2);

        Assert.assertEquals(expectedOutput, targetParent.getListOfChildBudgetItems());
    }

    @Test
    public void cannot_add_duplicate_children_to_parent() {
        List<BudgetItem> expectedOutput = new ArrayList<>();
        expectedOutput.add(targetChild1);
        expectedOutput.add(targetChild2);

        targetParent.addChild(targetChild1);

        Assert.assertEquals(expectedOutput, targetParent.getListOfChildBudgetItems());
    }

    @Test
    public void cannot_make_parent_its_own_child() {
        targetParent.addChild(targetParent);
        Assert.assertFalse(targetParent.getListOfChildBudgetItems().contains(targetParent));
    }

    @Test
    public void cannot_make_child_its_own_parent() {
        targetParent.setParent(targetParent);
        Assert.assertFalse(targetParent.getListOfChildBudgetItems().contains(targetParent));
    }

    @Test
    public void set_parent_budget_item_to_new_parent() {
        BudgetItem targetGrandchild1 = new BudgetItem();

        targetGrandchild1.setParent(targetChild1);  // Adds targetGrandchild1 to targetChild1
        targetGrandchild1.setParent(targetChild2);  // Removes targetGrandchild1 from targetChild1

        Assert.assertTrue(targetChild2.getListOfChildBudgetItems().contains(targetGrandchild1));
        Assert.assertTrue(targetChild1.getListOfChildBudgetItems().isEmpty());
    }

    @Test
    public void add_child_that_already_has_a_parent() {
        BudgetItem targetGrandchild1 = new BudgetItem();

        targetChild1.addChild(targetGrandchild1);  // Adds targetGrandchild1 to targetChild1
        targetChild2.addChild(targetGrandchild1);  // Removes targetGrandchild1 from targetChild1

        Assert.assertTrue(targetChild2.getListOfChildBudgetItems().contains(targetGrandchild1));
        Assert.assertTrue(targetChild1.getListOfChildBudgetItems().isEmpty());
    }

    @Test
    public void added_child_cannot_be_an_ancestor_of_parent() {
        BudgetItem targetGrandchild1 = new BudgetItem();
        BudgetItem targetGreatGrandchild1 = new BudgetItem();

        targetChild1.addChild(targetGrandchild1);
        targetGrandchild1.addChild(targetGreatGrandchild1);

        // The targetGreatGrandchild1 should not be able to have targetParent as a child since that is its ancestor
        targetGreatGrandchild1.addChild(targetParent);

        Assert.assertTrue(targetGreatGrandchild1.getListOfChildBudgetItems().isEmpty());
    }

    @Test
    public void child_inherits_recurring_transaction_information_from_parent_when_parent_is_given_in_constructor_parameters() {
        LocalDate firstTransactionDate = LocalDate.parse("2023-02-01");
        LocalDate lastTransactionDate = LocalDate.parse("2023-04-01");

        targetChild1.setupRecurringTransactions(3, "w", firstTransactionDate, lastTransactionDate);

        BudgetItem targetGrandchild1 = new BudgetItem("Food", targetChild1);

        Assert.assertEquals(firstTransactionDate, targetGrandchild1.getDateOfFirstTransaction());
        Assert.assertEquals(lastTransactionDate, targetGrandchild1.getDateOfLastTransaction());

        Assert.assertEquals(3, targetGrandchild1.getRecurringPaymentIntervalNumber());
        Assert.assertEquals(targetChild1.getWeekUnit(), targetGrandchild1.getRecurringPaymentIntervalTimeUnit());
    }


    /*******************************************************************************************************************
     * Testing the getTotalTransactionAmount() to get the total transaction amount
     */
    @Test
    public void gets_total_transaction_amount_from_children() {
        targetChild1.setTransactionAmount(5.23);
        targetChild2.setTransactionAmount(6.27);

        BigDecimal expectedOutput = BigDecimal.valueOf(5.23).add(BigDecimal.valueOf(6.27));

        Assert.assertEquals(expectedOutput, targetParent.getTotalTransactionAmount());
    }

    @Test
    public void gets_total_transaction_amount_from_children_multiple_generations() {
        targetChild1.setTransactionAmount(5.01);

        BudgetItem targetGrandchild1 = new BudgetItem();
        targetGrandchild1.setTransactionAmount(1.01);
        BudgetItem targetGrandchild2 = new BudgetItem();
        targetGrandchild2.setTransactionAmount(2.01);
        BudgetItem targetGrandchild3 = new BudgetItem();
        targetGrandchild3.setTransactionAmount(3.01);

        targetChild2.addChild(targetGrandchild1);
        targetChild2.addChild(targetGrandchild2);
        targetChild2.addChild(targetGrandchild3);

        BigDecimal expectedOutput = BigDecimal.valueOf(11.04);

        Assert.assertEquals(expectedOutput, targetParent.getTotalTransactionAmount());
    }


    /*******************************************************************************************************************
     * Testing Methods that set up transaction dates
     */
    @Test
    public void last_transaction_date_cannot_be_before_first_transaction_date() {
        // Set first and last transaction date to "2023-06-01"
        targetChild1.setupOneTimeTransaction(LocalDate.parse("2023-06-01"));

        targetChild1.setDateOfLastTransaction(LocalDate.parse("2023-01-01"));

        LocalDate expectedOutput = LocalDate.parse("2023-06-01");

        Assert.assertEquals(expectedOutput, targetChild1.getDateOfLastTransaction());
    }

    @Test
    public void first_transaction_date_cannot_be_after_last_transaction_date() {
        // Set first and last transaction date to "2023-06-01"
        targetChild1.setupOneTimeTransaction(LocalDate.parse("2023-06-01"));

        targetChild1.setDateOfFirstTransaction(LocalDate.parse("2023-12-01"));

        LocalDate expectedOutput = LocalDate.parse("2023-06-01");

        Assert.assertEquals(expectedOutput, targetChild1.getDateOfFirstTransaction());
    }

    @Test
    public void first_transaction_date_cannot_be_set_to_null() {
        Assert.assertNotEquals(null, targetChild1.getDateOfFirstTransaction());

        targetChild1.setDateOfFirstTransaction(null);

        Assert.assertNotEquals(null, targetChild1.getDateOfFirstTransaction());
    }

    @Test
    public void last_transaction_date_can_be_set_to_null() {
        targetChild1.setDateOfLastTransaction(null);

        Assert.assertEquals(null, targetChild1.getDateOfLastTransaction());
    }


    /*******************************************************************************************************************
     * Testing the getDateOfRecurringTransactionsWithInterval() Method
     * to get a list of transaction dates
     */
    @Test
    public void get_date_of_recurring_transaction_by_interval_no_interval_months() {
        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");

        targetChild1.setupRecurringTransactions (1, "M",startDate, endDate);

        LocalDate actualOutput = targetChild1.getDateOfRecurringTransactionsWithInterval(0);
        LocalDate expectedOutput = LocalDate.parse("2024-01-01");

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void get_date_of_recurring_transaction_by_interval_first_recurring_month() {
        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");

        targetChild1.setupRecurringTransactions (1, "M", startDate, endDate);

        LocalDate actualOutput = targetChild1.getDateOfRecurringTransactionsWithInterval(1);
        LocalDate expectedOutput = LocalDate.parse("2024-02-01");

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void get_date_of_recurring_transaction_by_interval_negative_interval_months() {
        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");

        targetChild1.setupRecurringTransactions (1, "M", startDate, endDate);

        LocalDate actualOutput = targetChild1.getDateOfRecurringTransactionsWithInterval(-1);
        LocalDate expectedOutput = LocalDate.parse("2023-12-01");

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void get_date_of_recurring_transaction_by_interval_many_months() {
        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");

        targetChild1.setupRecurringTransactions (1, "M", startDate, endDate);

        LocalDate actualOutput = targetChild1.getDateOfRecurringTransactionsWithInterval(5);
        LocalDate expectedOutput = LocalDate.parse("2024-06-01");

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void get_date_of_recurring_transaction_by_interval_no_interval_weeks() {
        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");

        targetChild1.setupRecurringTransactions (1, "W", startDate, endDate);

        LocalDate actualOutput = targetChild1.getDateOfRecurringTransactionsWithInterval(0);
        LocalDate expectedOutput = LocalDate.parse("2024-01-01");

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void get_date_of_recurring_transaction_by_interval_first_recurring_week() {
        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");

        targetChild1.setupRecurringTransactions (1, "W", startDate, endDate);

        LocalDate actualOutput = targetChild1.getDateOfRecurringTransactionsWithInterval(1);
        LocalDate expectedOutput = LocalDate.parse("2024-01-08");

        Assert.assertEquals(expectedOutput, actualOutput);
    }


    @Test
    public void get_date_of_recurring_transaction_by_interval_negative_interval_weeks() {
        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");

        targetChild1.setupRecurringTransactions (1, "W", startDate, endDate);

        LocalDate actualOutput = targetChild1.getDateOfRecurringTransactionsWithInterval(-1);
        LocalDate expectedOutput = LocalDate.parse("2023-12-25");

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void get_date_of_recurring_transaction_by_interval_many_weeks() {
        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");

        targetChild1.setupRecurringTransactions (1, "W", startDate, endDate);

        LocalDate actualOutput = targetChild1.getDateOfRecurringTransactionsWithInterval(3);
        LocalDate expectedOutput = LocalDate.parse("2024-01-22");

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void get_date_of_recurring_transaction_by_interval_no_interval_days() {
        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");

        targetChild1.setupRecurringTransactions (1, "D", startDate, endDate);

        LocalDate actualOutput = targetChild1.getDateOfRecurringTransactionsWithInterval(0);
        LocalDate expectedOutput = LocalDate.parse("2024-01-01");

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void get_date_of_recurring_transaction_by_interval_first_recurring_day() {
        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");

        targetChild1.setupRecurringTransactions (1, "D", startDate, endDate);

        LocalDate actualOutput = targetChild1.getDateOfRecurringTransactionsWithInterval(1);
        LocalDate expectedOutput = LocalDate.parse("2024-01-02");

        Assert.assertEquals(expectedOutput, actualOutput);
    }


    @Test
    public void get_date_of_recurring_transaction_by_interval_negative_interval_days() {
        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");

        targetChild1.setupRecurringTransactions (1, "D", startDate, endDate);

        LocalDate actualOutput = targetChild1.getDateOfRecurringTransactionsWithInterval(-1);
        LocalDate expectedOutput = LocalDate.parse("2023-12-31");

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void get_date_of_recurring_transaction_by_interval_many_days() {
        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");

        targetChild1.setupRecurringTransactions (1, "D", startDate, endDate);

        LocalDate actualOutput = targetChild1.getDateOfRecurringTransactionsWithInterval(15);
        LocalDate expectedOutput = LocalDate.parse("2024-01-16");

        Assert.assertEquals(expectedOutput, actualOutput);
    }


    /*******************************************************************************************************************
     * Testing the recurringTransactionDatesInDateRangeForThisBudgetItem() method
     * to get the total transaction amount in the time range
     */

    /** Making sure the list of all transaction dates are given in a time range */
    @Test
    public void lists_transaction_dates_between_a_date_range() {
        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-03-01");

        targetChild1.setupRecurringTransactions (1, "M", startDate, endDate);

        List<LocalDate> actualOutput
                = targetChild1.recurringTransactionDatesInDateRangeForThisBudgetItem(startDate, endDate);

        List<LocalDate> expectedOutput = new ArrayList<>();
        expectedOutput.add(LocalDate.parse("2024-01-01"));
        expectedOutput.add(LocalDate.parse("2024-02-01"));
        expectedOutput.add(LocalDate.parse("2024-03-01"));

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void lists_transaction_dates_between_a_date_range_when_start_date_is_before_first_transaction() {
        LocalDate firstTransactionDate = LocalDate.parse("2024-06-01");

        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-08-01");

        targetChild1.setupRecurringTransactions (1, "M", startDate, endDate);

        List<LocalDate> actualOutput
                = targetChild1.recurringTransactionDatesInDateRangeForThisBudgetItem(firstTransactionDate, endDate);

        List<LocalDate> expectedOutput = new ArrayList<>();
        expectedOutput.add(LocalDate.parse("2024-06-01"));
        expectedOutput.add(LocalDate.parse("2024-07-01"));
        expectedOutput.add(LocalDate.parse("2024-08-01"));

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void lists_transaction_dates_between_a_date_range_when_start_date_is_after_first_transaction() {
        LocalDate firstTransactionDate = LocalDate.parse("2024-01-01");

        LocalDate startDate = LocalDate.parse("2024-06-01");
        LocalDate endDate = LocalDate.parse("2024-08-01");

        targetChild1.setupRecurringTransactions (1, "M", startDate, endDate);

        List<LocalDate> actualOutput
                = targetChild1.recurringTransactionDatesInDateRangeForThisBudgetItem(startDate, endDate);

        List<LocalDate> expectedOutput = new ArrayList<>();
        expectedOutput.add(LocalDate.parse("2024-06-01"));
        expectedOutput.add(LocalDate.parse("2024-07-01"));
        expectedOutput.add(LocalDate.parse("2024-08-01"));

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void lists_transaction_dates_between_a_date_range_when_end_date_is_before_last_transaction() {
        LocalDate lastTransactionDate = LocalDate.parse("2024-12-01");

        LocalDate startDate = LocalDate.parse("2024-06-01");
        LocalDate endDate = LocalDate.parse("2024-08-01");

        targetChild1.setupRecurringTransactions (1, "M", startDate, lastTransactionDate);

        List<LocalDate> actualOutput
                = targetChild1.recurringTransactionDatesInDateRangeForThisBudgetItem(startDate, endDate);

        List<LocalDate> expectedOutput = new ArrayList<>();
        expectedOutput.add(LocalDate.parse("2024-06-01"));
        expectedOutput.add(LocalDate.parse("2024-07-01"));
        expectedOutput.add(LocalDate.parse("2024-08-01"));

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void lists_transaction_dates_between_a_date_range_when_end_date_is_after_last_transaction() {
        LocalDate lastTransactionDate = LocalDate.parse("2024-08-01");

        LocalDate startDate = LocalDate.parse("2024-06-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");

        targetChild1.setupRecurringTransactions (1, "M", startDate, lastTransactionDate);

        List<LocalDate> actualOutput
                = targetChild1.recurringTransactionDatesInDateRangeForThisBudgetItem(startDate, endDate);

        List<LocalDate> expectedOutput = new ArrayList<>();
        expectedOutput.add(LocalDate.parse("2024-06-01"));
        expectedOutput.add(LocalDate.parse("2024-07-01"));
        expectedOutput.add(LocalDate.parse("2024-08-01"));

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void lists_transaction_dates_between_a_date_range_when_end_date_and_start_date_are_the_same() {
        LocalDate firstTransactionDate = LocalDate.parse("2024-05-01");
        LocalDate lastTransactionDate = LocalDate.parse("2024-12-01");

        LocalDate startDate = LocalDate.parse("2024-07-01");
        LocalDate endDate = LocalDate.parse("2024-07-01");

        targetChild1.setupRecurringTransactions (1, "M", firstTransactionDate, lastTransactionDate);

        List<LocalDate> actualOutput
                = targetChild1.recurringTransactionDatesInDateRangeForThisBudgetItem(startDate, endDate);

        List<LocalDate> expectedOutput = new ArrayList<>();
        expectedOutput.add(LocalDate.parse("2024-07-01"));

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void lists_transaction_dates_between_a_date_range_when_first_and_last_transaction_days_are_same() {
        LocalDate transactionDate = LocalDate.parse("2024-05-01");

        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");

        targetChild1.setupOneTimeTransaction(transactionDate);

        List<LocalDate> actualOutput
                = targetChild1.recurringTransactionDatesInDateRangeForThisBudgetItem(startDate, endDate);

        List<LocalDate> expectedOutput = new ArrayList<>();
        expectedOutput.add(LocalDate.parse("2024-05-01"));

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void lists_transaction_dates_between_a_date_range_when_last_transaction_is_before_start_date() {
        LocalDate firstTransactionDate = LocalDate.parse("2024-01-01");
        LocalDate lastTransactionDate = LocalDate.parse("2024-05-01");

        LocalDate startDate = LocalDate.parse("2024-07-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");

        targetChild1.setupRecurringTransactions(1, "M", firstTransactionDate, lastTransactionDate);

        List<LocalDate> actualOutput
                = targetChild1.recurringTransactionDatesInDateRangeForThisBudgetItem(startDate, endDate);

        List<LocalDate> expectedOutput = new ArrayList<>();

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void lists_transaction_dates_between_a_date_range_when_first_transaction_is_after_end_date() {
        LocalDate firstTransactionDate = LocalDate.parse("2024-07-01");
        LocalDate lastTransactionDate = LocalDate.parse("2024-12-01");

        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-05-01");

        targetChild1.setupRecurringTransactions(1, "M", firstTransactionDate, lastTransactionDate);

        List<LocalDate> actualOutput
                = targetChild1.recurringTransactionDatesInDateRangeForThisBudgetItem(startDate, endDate);

        List<LocalDate> expectedOutput = new ArrayList<>();

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void gives_empty_list_of_transaction_dates_between_a_date_range_when_start_or_end_dates_are_null() {
        LocalDate firstTransactionDate = LocalDate.parse("2024-07-01");
        LocalDate lastTransactionDate = LocalDate.parse("2024-12-01");

        LocalDate startDate = null;
        LocalDate endDate = null;

        targetChild1.setupRecurringTransactions(1, "M", firstTransactionDate, lastTransactionDate);

        List<LocalDate> actualOutput
                = targetChild1.recurringTransactionDatesInDateRangeForThisBudgetItem(startDate, endDate);

        List<LocalDate> expectedOutput = new ArrayList<>();

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    /** Testing different values of recurringPaymentIntervalNumber besides One*/
    @Test
    public void gives_empty_list_of_transaction_dates_when_recurringPaymentIntervalNumber_is_zero() {
        int recurringPaymentIntervalNumber = 0;

        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");

        targetChild1.setupRecurringTransactions(recurringPaymentIntervalNumber, "M", startDate, endDate);

        List<LocalDate> actualOutput
                = targetChild1.recurringTransactionDatesInDateRangeForThisBudgetItem(startDate, endDate);

        List<LocalDate> expectedOutput = new ArrayList<>();

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void list_of_transaction_dates_when_recurringPaymentIntervalNumber_is_two() {
        int recurringPaymentIntervalNumber = 2;

        LocalDate firstTransactionDate = LocalDate.parse("2024-03-01");
        LocalDate lastTransactionDate = LocalDate.parse("2024-08-01");

        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");

        targetChild1.setupRecurringTransactions(recurringPaymentIntervalNumber, "M", firstTransactionDate, lastTransactionDate);

        List<LocalDate> actualOutput
                = targetChild1.recurringTransactionDatesInDateRangeForThisBudgetItem(startDate, endDate);

        List<LocalDate> expectedOutput = new ArrayList<>();
        expectedOutput.add(LocalDate.parse("2024-03-01"));
        expectedOutput.add(LocalDate.parse("2024-05-01"));
        expectedOutput.add(LocalDate.parse("2024-07-01"));

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void gives_empty_list_of_transaction_dates_when_recurringPaymentIntervalNumber_is_negative() {
        int recurringPaymentIntervalNumber = -1;

        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");

        targetChild1.setupRecurringTransactions(recurringPaymentIntervalNumber, "M", startDate, endDate);

        List<LocalDate> actualOutput
                = targetChild1.recurringTransactionDatesInDateRangeForThisBudgetItem(startDate, endDate);

        List<LocalDate> expectedOutput = new ArrayList<>();

        Assert.assertEquals(expectedOutput, actualOutput);
    }


    /*******************************************************************************************************************
     * Testing that BudgetItems with children cannot have individual prices set
     */
    @Test
    public void parents_cannot_have_personal_transaction_amounts() {
        targetChild1.setTransactionAmount(20.01);  // Setting amount before having children

        BudgetItem targetGrandchild1 = new BudgetItem();
        targetGrandchild1.setParent(targetChild1);

        targetChild1.setTransactionAmount(20.01);  // Trying to set amount after having children

        Assert.assertEquals(BigDecimal.valueOf(0.0), targetChild1.getTotalTransactionAmount());
    }

    @Test
    public void parents_can_have_personal_transaction_amounts_after_child_has_been_removed() {
        targetChild1.setTransactionAmount(3.01);

        BudgetItem targetGrandchild1 = new BudgetItem();
        targetGrandchild1.setParent(targetChild1);  // Adds targetGrandchild1 under targetChild1
        targetGrandchild1.setParent(targetChild2);  // Removes targetGrandchild1 from targetChild1

        targetChild1.setTransactionAmount(20.01);

        Assert.assertEquals(BigDecimal.valueOf(20.01), targetChild1.getTotalTransactionAmount());
    }


    /*******************************************************************************************************************
     * Testing the totalRecurringTransactionAmountBetweenDateRange() method
     * to setup transactions for one BudgetItem with no children
     */
    @Test
    public void setup_one_time_transaction() {
        targetChild1.setTransactionAmount(10.01);
        targetChild1.setupOneTimeTransaction(LocalDate.parse("2024-02-01"));

        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");
        BigDecimal actualOutput = targetChild1.totalRecurringTransactionAmountBetweenDateRange(startDate, endDate);

        BigDecimal expectedOutput = BigDecimal.valueOf(10.01);

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void setup_recurring_transaction_with_no_end_date () {
        LocalDate firstTransactionDate = LocalDate.parse("2024-01-15");
        targetChild1.setTransactionAmount(10.01);
        targetChild1.setupRecurringTransactionNoEndDate(1, "m", firstTransactionDate);

        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-31");
        BigDecimal actualOutput = targetChild1.totalRecurringTransactionAmountBetweenDateRange(startDate, endDate);

        BigDecimal expectedOutput = BigDecimal.valueOf(120.12);

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void gives_transaction_amount_of_zero_when_a_one_time_transaction_is_before_date_range() {
        targetChild1.setTransactionAmount(10.01);
        targetChild1.setupOneTimeTransaction(LocalDate.parse("2024-03-01"));

        LocalDate startDate = LocalDate.parse("2024-03-02");
        LocalDate endDate = LocalDate.parse("2024-12-01");
        BigDecimal actualOutput = targetChild1.totalRecurringTransactionAmountBetweenDateRange(startDate, endDate);
        actualOutput = actualOutput.stripTrailingZeros();

        BigDecimal expectedOutput = BigDecimal.valueOf(0);

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void gives_transaction_amount_of_zero_when_a_one_time_transaction_is_after_date_range() {
        targetChild1.setTransactionAmount(10.01);
        targetChild1.setupOneTimeTransaction(LocalDate.parse("2024-12-02"));

        LocalDate startDate = LocalDate.parse("2024-03-02");
        LocalDate endDate = LocalDate.parse("2024-12-01");
        BigDecimal actualOutput = targetChild1.totalRecurringTransactionAmountBetweenDateRange(startDate, endDate);
        actualOutput = actualOutput.stripTrailingZeros();

        BigDecimal expectedOutput = BigDecimal.valueOf(0);

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void when_first_and_last_recurring_transaction_dates_are_within_a_time_range() {
        targetChild1.setTransactionAmount(10.01);
        LocalDate firstTransactionDate = LocalDate.parse("2024-03-01");
        LocalDate lastTransactionDate = LocalDate.parse("2024-03-30");
        targetChild1.setupRecurringTransactions(3, "d", firstTransactionDate, lastTransactionDate);

        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");
        BigDecimal actualOutput = targetChild1.totalRecurringTransactionAmountBetweenDateRange(startDate, endDate);
        actualOutput = actualOutput.stripTrailingZeros();

        BigDecimal expectedOutput = BigDecimal.valueOf(100.1);

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void when_first_and_last_recurring_transaction_dates_are_outside_a_time_range() {
        targetChild1.setTransactionAmount(10.01);
        LocalDate firstTransactionDate = LocalDate.parse("2023-03-01");
        LocalDate lastTransactionDate = LocalDate.parse("2025-03-30");
        targetChild1.setupRecurringTransactions(1, "m", firstTransactionDate, lastTransactionDate);

        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");
        BigDecimal actualOutput = targetChild1.totalRecurringTransactionAmountBetweenDateRange(startDate, endDate);
        actualOutput = actualOutput.stripTrailingZeros();

        BigDecimal expectedOutput = BigDecimal.valueOf(120.12);

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void when_last_transaction_date_is_not_on_a_transaction_interval() {
        targetChild1.setTransactionAmount(10.01);
        LocalDate firstTransactionDate = LocalDate.parse("2024-04-01");
        LocalDate lastTransactionDate = LocalDate.parse("2024-05-15");
        targetChild1.setupRecurringTransactions(1, "m", firstTransactionDate, lastTransactionDate);

        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate endDate = LocalDate.parse("2024-12-01");
        BigDecimal actualOutput = targetChild1.totalRecurringTransactionAmountBetweenDateRange(startDate, endDate);
        actualOutput = actualOutput.stripTrailingZeros();

        BigDecimal expectedOutput = BigDecimal.valueOf(20.02);

        Assert.assertEquals(expectedOutput, actualOutput);
    }


    /*******************************************************************************************************************
     * Testing the totalRecurringTransactionAmountBetweenDateRange() method
     * to get total payments between a date range for multiple Budget Items
     */
    @Test
    public void calculates_total_for_multiple_transaction_within_date_range() {
        LocalDate firstTransactionDate = LocalDate.parse("2024-01-01");
        LocalDate lastTransactionDate = LocalDate.parse("2024-12-31");

        BudgetItem targetChild3 = new BudgetItem("Income", targetParent);
        targetChild3.setupRecurringTransactionNoEndDate(1, "M", firstTransactionDate);

        // +$12,000 a year
        BudgetItem targetGrandchild1 = new BudgetItem("Paycheck", targetChild3);
        targetGrandchild1.setTransactionAmount(1000.00);

        // +$3,000 a year
        BudgetItem targetGrandchild2 = new BudgetItem("Bi-monthly Bonus", targetChild3);
        targetGrandchild2.setTransactionAmount(500);
        targetGrandchild2.setupRecurringTransactions(2, "M", firstTransactionDate, lastTransactionDate);

        // +$1,000 a year
        BudgetItem targetGrandchild3 = new BudgetItem("End of Year Bonus", targetChild3);
        targetGrandchild3.setTransactionAmount(1000.01);
        targetGrandchild3.setupOneTimeTransaction(lastTransactionDate);

        // -2700$ a year
        targetChild2 = new BudgetItem("Groceries", targetParent);
        targetChild2.setTransactionAmount(-100);
        targetChild2.setupRecurringTransactionNoEndDate(2, "W", firstTransactionDate);


        // Get the output
        BigDecimal actualOutput = targetParent.totalRecurringTransactionAmountBetweenDateRange(firstTransactionDate, lastTransactionDate);
        actualOutput = actualOutput.stripTrailingZeros();

        // Set the expected output
        BigDecimal expectedOutput = BigDecimal.valueOf(13300.01);

        Assert.assertEquals(expectedOutput, actualOutput);
    }

/*








 */


}
