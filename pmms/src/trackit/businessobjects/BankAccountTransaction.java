/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Owner
 */
public class BankAccountTransaction {
    private Long id;
    private BankTransactionType transactionType;
    private BigDecimal amount;
    private Date date;
    private String checkNumber;
    private Long bankAccountId;
    private Long userId;
    private String description;
    private Long initiatorId;
    private TransactionReason transactionReason;   // Money Order purchase, Loan, etc.

    public BankAccountTransaction() {}

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BankTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(BankTransactionType transactionTypeId) {
        this.transactionType = transactionTypeId;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(Long initiatorId) {
        this.initiatorId = initiatorId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TransactionReason getTransactionReason() {
        return transactionReason;
    }

    public void setTransactionReason(TransactionReason transactionReason) {
        this.transactionReason = transactionReason;
    }

    
}
