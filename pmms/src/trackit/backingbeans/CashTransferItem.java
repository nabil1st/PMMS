/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ndaoud
 */
public class CashTransferItem implements IDatedItem {

    private Long id;
    private Date date;
    private String dateStr;
    
    private BigDecimal amount;
    
    private String sourceAccountName;
    private String destinationAccountName;
    private String notes;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Date getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    
    public void setDate(Date date) {
        this.date = date;
    }

	public String getSourceAccountName() {
		return sourceAccountName;
	}

	public void setSourceAccountName(String sourceAccountName) {
		this.sourceAccountName = sourceAccountName;
	}

	public String getDestinationAccountName() {
		return destinationAccountName;
	}

	public void setDestinationAccountName(String destinationAccountName) {
		this.destinationAccountName = destinationAccountName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String note) {
		this.notes = note;
	}   
        
}
