/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Owner
 */
public class CurrencyOnHand extends Entity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date date;
    private BigDecimal amount;
    private Long sourceId;
    private Long groupId;
    
    private CurrencyOnHandType type;
    
    private Long userId;
    private CurrencyOnHandSourceType sourceType;
    private String checkNumber;

    private Long transactionId;
    private TransactionType transactionType;

    private boolean used;
    private Map<Long, IncomeSource> incomeSourceLookup;

    // The id of the entity referenced by this currency
    // For example this will contain the LoanId for a Loan payment.
    private Long referenceId;


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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public CurrencyOnHandSourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(CurrencyOnHandSourceType sourceType) {
        this.sourceType = sourceType;
    }
    
    public Long getSourceId() {
        return sourceId;
    }

    public void setIncomeSourceLookup(Map<Long, IncomeSource> incomeSourceLookup) {
        this.incomeSourceLookup = incomeSourceLookup;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public CurrencyOnHandType getType() {
        return type;
    }

    public void setType(CurrencyOnHandType type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
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

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }    

    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append(amount.toString());
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        buff.append(" - ").append(format.format(date));
        if (checkNumber != null && checkNumber.length() > 0) {
            buff.append(" (").append(checkNumber).append(")");
        }

        if (incomeSourceLookup != null && sourceId != null && incomeSourceLookup.containsKey(sourceId)) {
            buff.append(" - ").append(incomeSourceLookup.get(sourceId).getName());
        }
        

        return buff.toString();
    }

    

}
