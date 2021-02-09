/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.math.BigDecimal;
import java.util.Date;

import trackit.businessobjects.CurrencyOnHandSourceType;

/**
 *
 * @author ndaoud
 */
public class CurrencyOnHandHistoryItem implements IDatedItem, IListItem {

	private Long id;
    private Date date;
    private String dateStr;
    private String source;
    private String currencyType;
    private BigDecimal amount;
    private String group;
    private String description;
    private String onHand;
    
    private CurrencyOnHandSourceType currencyOnHandSourceType;

	public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceType() {
    	if (currencyOnHandSourceType != null) {
            return currencyOnHandSourceType.toString();
        } else {
        	return "";
        }
    }
    

    public String getOnHand() {
        return onHand;
    }

    public void setOnHand(String onHand) {
        this.onHand = onHand;
    }

	@Override
	public Long getId() {
		return this.id;
	}
		
	public void setId(Long id) {
		this.id = id;
	}

	public CurrencyOnHandSourceType getCurrencyOnHandSourceType() {
		return currencyOnHandSourceType;
	}

	public void setCurrencyOnHandSourceType(
			CurrencyOnHandSourceType currencyOnHandSourceType) {
		this.currencyOnHandSourceType = currencyOnHandSourceType;
	}
	
	

}
