/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.businessobjects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Owner
 */
public class ExpenseItem {
    private Long id;
    private BigDecimal amount;
    private Long expenseTypeId;
    private Long expenseGroupId;
    private String description;
    private BigDecimal tax;
    private ExpensePurpose expensePurpose;
    private Long borrowerId;
    
    private Map<Long,String> expenseTypeLookup;
    private Map<Long,String> expenseGroupLookup;
    private Map<Long,String> borrowerLookup;
    
    private List<String> selectedTaxes = new ArrayList<String>();

    public void setExpenseTypeLookup(Map<Long,String> lookup) {
    	this.expenseTypeLookup = lookup;
    }
    
    public void setExpenseGroupLookup(Map<Long,String> lookup) {
    	this.expenseGroupLookup = lookup;
    }    
    
    public void setBorrowerLookup(Map<Long,String> lookup) {
    	this.borrowerLookup = lookup;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getExpenseGroupId() {
        return expenseGroupId;
    }

    public void setExpenseGroupId(Long expenseGroupId) {
        this.expenseGroupId = expenseGroupId;
    }

    public Long getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(Long expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }	
	
	public ExpensePurpose getExpensePurpose() {
		return expensePurpose;
	}

	public void setExpensePurpose(ExpensePurpose expensePurpose) {
		this.expensePurpose = expensePurpose;
	}
	
	public String getExpensePurposeName() {
		if (expensePurpose != null) {
			return this.expensePurpose.toString();
		}
		
		return "";
	}

	public String getExpenseType() {
		return expenseTypeLookup.get(getExpenseTypeId());
	}
	
	public String getExpenseGroup() {
		return expenseGroupLookup.get(getExpenseGroupId());
	}

	public Long getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(Long borrowerId) {
		this.borrowerId = borrowerId;
	}
	
	public String getBorrowerName() {
		if (borrowerId != null) {
			return borrowerLookup.get(borrowerId);
		}
		
		return "";
	}

	public List<String> getSelectedTaxes() {
		return selectedTaxes;
	}

	public void setSelectedTaxes(List<String> selectedTaxes) {
		this.selectedTaxes = selectedTaxes;
	}
	
	@Override
	public Object clone() {
		ExpenseItem clone = new ExpenseItem();
		clone.setAmount(amount);
		clone.setBorrowerId(borrowerId);
		clone.setDescription(description);
		clone.setExpenseGroupId(expenseGroupId);
		clone.setExpensePurpose(expensePurpose);
		clone.setExpenseTypeId(expenseTypeId);
		clone.setId(id);
		clone.setTax(tax);
		return clone;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ExpenseItem) {
			ExpenseItem eq = (ExpenseItem) obj;
			
			return objectsEqual(this.id, eq.getId()) &&
				objectsEqual(this.amount, eq.getAmount()) &&
				objectsEqual(this.borrowerId, eq.getBorrowerId()) &&
				objectsEqual(this.description, eq.getDescription()) &&
				objectsEqual(this.expenseGroupId, eq.getExpenseGroupId()) &&
				objectsEqual(this.expensePurpose, eq.getExpensePurpose()) &&
				objectsEqual(this.expenseTypeId, eq.getExpenseTypeId()) &&
				objectsEqual(this.tax, eq.getTax());
		}
		
		return false;
	}
	
	
	private boolean objectsEqual(Object obj1, Object obj2) {
		
		if (obj1 == null && obj2 == null) {
			return true;
		} else if (obj1 == null && obj2 != null || obj1 != null && obj2 == null) {
			return false;
		} else {		
			if (obj1 instanceof ExpensePurpose) {
				ExpensePurpose ep1 = (ExpensePurpose) obj1;
				ExpensePurpose ep2 = (ExpensePurpose) obj2;
				return ep1.toInt() == ep2.toInt();
			} else { 
				return obj1.equals(obj2);
			}
		}
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (this.borrowerId != null) {
			hashCode += this.borrowerId.hashCode();
		}
		
		if (this.amount != null) {
			hashCode += this.amount.hashCode();
		}
		
		if (this.description != null) {
			hashCode += this.description.hashCode();
		}
		
		if (this.expenseGroupId != null) {
			hashCode += this.expenseGroupId.hashCode();
		}
		
		if (this.expensePurpose != null) {
			hashCode += this.expensePurpose.toInt() * 7;
		}
		
		if (this.expenseTypeId != null) {
			hashCode += this.expenseTypeId.hashCode();
		}
		
		if (this.id != null) {
			hashCode += this.id.hashCode();
		}
		
		if (this.tax != null) {
			hashCode += this.tax.hashCode();
		}
		
		return hashCode;
	}
	
	
    
}
