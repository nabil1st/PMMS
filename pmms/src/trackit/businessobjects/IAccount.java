/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

import java.util.Set;

/**
 *
 * @author Owner
 */
public interface IAccount {

    public String getAccountDescription();

    public void setAccountDescription(String accountDescription);

    public Long getId();

    public void setId(Long accountId);

    public Set<User> getUsers();

    public void setUsers(Set<User> users);

    public Long getPrimaryUserId();

    public void setPrimaryUserId(Long primaryUserId);

    public Set<BankAccount> getBankAccounts();

    public void setBankAccounts(Set<BankAccount> bankAccounts);

    public Set<CreditCard> getCreditCards();

    public void setCreditCards(Set<CreditCard> creditCards);

    public Set<ExpenseGroup> getExpenseGroups();

    public void setExpenseGroups(Set<ExpenseGroup> expenseGroups);

    public Set<ExpenseType> getExpenseTypes();

    public void setExpenseTypes(Set<ExpenseType> expenseTypes);

    public Set<Payee> getPayees();

    public void setPayees(Set<Payee> payees);

    public Set<Expense> getExpenses();

    public void setExpenses(Set<Expense> expenses);

}
