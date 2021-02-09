/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

import java.math.BigDecimal;
import java.util.Date;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Owner
 */
@Root
public class BankAccount extends Entity {
	@Element
    private String description;
		
    private Long currentCheckNumber;
    
    @Element
    private Long accountTypeId;
    
    @Element
    private BigDecimal balance;
    
    private Date createdDate;
    private Long createdBy;
    private Date modifiedDate;
    private Long modifiedBy;
    
    private boolean inSync;

//    private Set<BankAccountTransaction> transactions;

    public BankAccount() {}

    public Long getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Long accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public Long getCurrentCheckNumber() {
        return currentCheckNumber;
    }

    public void setCurrentCheckNumber(Long currentCheckNumber) {
        this.currentCheckNumber = currentCheckNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void addToBalance(BigDecimal amount) {
        this.balance = getCurrentBalance().add(amount);
    }

    public void subtractFromBalance(BigDecimal amount) {
        this.balance = getCurrentBalance().subtract(amount);
    }

    private BigDecimal getCurrentBalance() {
        if (this.balance == null) {
            return new BigDecimal(0);
        }

        return this.balance;
    }

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createBy) {
		this.createdBy = createBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
	public boolean isInSync() {
		return inSync;
	}

	public void setInSync(boolean inSync) {
		this.inSync = inSync;
	}

	@Override
	public String toString() {
		return this.description;
	}

}
