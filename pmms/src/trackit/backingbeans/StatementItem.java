/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.math.BigDecimal;
import java.util.Date;
import trackit.businessobjects.BankTransactionType;

/**
 *
 * @author ndaoud
 */
public class StatementItem {
    private Date date;
    private String dateStr;
    private String description;
    private String to;
    private BigDecimal amount;
    private BankTransactionType transactionType;


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BankTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(BankTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

}
