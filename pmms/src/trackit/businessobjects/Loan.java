/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Owner
 */
public class Loan extends Entity {
    private Date date;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private Long bankAccountId;
    private String checkNumber;
    private Long creditCardId;
    private Long payeeId;
    private Long borrowerId;
    private String description;
    private Long userId;
    private Long moneyOrderId;
    private Long transactionId;
    private Long groupId;
    private TransactionType transactionType;

    private Set<CurrencyOnHand> loanPayments;

    private Map<Long,Payee> payeeLookup;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
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

    public Long getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(Long payeeId) {
        this.payeeId = payeeId;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMoneyOrderId() {
        return moneyOrderId;
    }

    public void setMoneyOrderId(Long moneyOrderId) {
        this.moneyOrderId = moneyOrderId;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Set<CurrencyOnHand> getLoanPayments() {
        return loanPayments;
    }

    public void setLoanPayments(Set<CurrencyOnHand> loanPayments) {
        this.loanPayments = loanPayments;
    }

    public void setPayeeLookup(Map<Long,Payee> payeeLookup) {
        this.payeeLookup = payeeLookup;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String toString() {
        StringBuffer buff = new StringBuffer();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        buff.append(format.format(date));
        buff.append(" - ").append(amount.toString());
        if (payeeLookup != null && payeeLookup.containsKey(this.borrowerId)) {
            buff.append(" - ").append(payeeLookup.get(this.borrowerId).getDescription());
        }

        return buff.toString();
    }


}
