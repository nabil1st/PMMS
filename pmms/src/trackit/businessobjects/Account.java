/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

import java.math.BigDecimal;
import java.util.Set;

/**
 *
 * @author Owner
 */
public class Account {
    private Long id;
    private String accountDescription;
    private Set<User> users;
    private Long primaryUserId;

    private Set<BankAccount> bankAccounts;
    private Set<CreditCard> creditCards;
    private Set<ExpenseGroup> expenseGroups;
    private Set<ExpenseType> expenseTypes;
    private Set<Payee> payees;
    private Set<Borrower> borrowers;
    private Set<Expense> expenses;
    private Set<IncomeSource> incomeSources;

    private BigDecimal cashBalance;

    public Account() {}

    public String getAccountDescription() {
        return accountDescription;
    }

    public void setAccountDescription(String accountDescription) {
        this.accountDescription = accountDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long accountId) {
        this.id = accountId;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Long getPrimaryUserId() {
        return primaryUserId;
    }

    public void setPrimaryUserId(Long primaryUserId) {
        this.primaryUserId = primaryUserId;
    }

    public Set<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(Set<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public Set<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Set<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public Set<ExpenseGroup> getExpenseGroups() {
        return expenseGroups;
    }

    public void setExpenseGroups(Set<ExpenseGroup> expenseGroups) {
        this.expenseGroups = expenseGroups;
    }

    public Set<ExpenseType> getExpenseTypes() {
        return expenseTypes;
    }

    public void setExpenseTypes(Set<ExpenseType> expenseTypes) {
        this.expenseTypes = expenseTypes;
    }

    public Set<Payee> getPayees() {
        return payees;
    }

    public void setPayees(Set<Payee> payees) {
        this.payees = payees;
    }

    public Set<Borrower> getBorrowers() {
        return borrowers;
    }

    public void setBorrowers(Set<Borrower> borrowers) {
        this.borrowers = borrowers;
    }

    public Set<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }

    public Set<IncomeSource> getIncomeSources() {
        return incomeSources;
    }

    public void setIncomeSources(Set<IncomeSource> incomeSources) {
        this.incomeSources = incomeSources;
    }

    public BigDecimal getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(BigDecimal cashBalance) {
        this.cashBalance = cashBalance;
    }

    public void addToBalance(BigDecimal amount) {
        this.cashBalance = getCurrentBalance().add(amount);
    }

    public void subtractFromBalance(BigDecimal amount) {
        this.cashBalance = getCurrentBalance().subtract(amount);
    }

    private BigDecimal getCurrentBalance() {
        if (this.cashBalance == null) {
            return new BigDecimal(0);
        }

        return this.cashBalance;
    }
}
