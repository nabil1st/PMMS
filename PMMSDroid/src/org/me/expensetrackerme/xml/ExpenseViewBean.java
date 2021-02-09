/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.expensetrackerme.xml;

/**
 *
 * @author HP
 */
public class ExpenseViewBean {
    private long expenseId;
    private String name;

    public ExpenseViewBean(long id, String name) {
        this.expenseId = id;
        this.name = name;
    }

    public long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(long expenseId) {
        this.expenseId = expenseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
    

}
