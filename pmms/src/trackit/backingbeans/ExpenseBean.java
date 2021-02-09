/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;


import trackit.businessobjects.Entity;
import trackit.businessobjects.Expense;
import trackit.businessobjects.ExpenseGroup;
import trackit.businessobjects.ExpensePurpose;
import trackit.util.ListUtils;

/**
 *
 * @author Nabil
 */
public final class ExpenseBean extends GenericExpenseBean implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long expenseTypeId;
    private Long expenseGroupId;
    private String description;
    private boolean refund = false;

    private Map<Long, ExpenseGroup> expenseGroupLookup;
    
    private ExpensePurpose expensePurpose;
    
    public ExpenseBean(){    	
    }
    
    public void editIt(Entity entity) {
    	Expense expense = (Expense) entity;
    	clearAllFields();
    	super.editIt(expense);    	
    	this.expenseTypeId = expense.getExpenseTypeId();
    	this.expenseGroupId = expense.getExpenseGroupId();
    	this.description = expense.getDescription();    	
    	this.expensePurpose = expense.getExpensePurpose();
    }
    
    public void refund(Entity entity) {
    	Expense expense = (Expense) entity;
    	clearAllFields();
    	super.editIt(expense);
    	
    	this.date = new Date();
    	this.amount = new BigDecimal(0);
    	
    	this.expenseTypeId = expense.getExpenseTypeId();
    	this.expenseGroupId = expense.getExpenseGroupId();    	   	
    	this.expensePurpose = expense.getExpensePurpose();
    	
    	this.editMode = false;
    	this.refund = true;
    }

    public Long getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(Long expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }

    public String getExpenseGroupName() {
        if (expenseGroupLookup != null && expenseGroupId != null) {
            ExpenseGroup eg = expenseGroupLookup.get(expenseGroupId);
            if (eg != null) {
                return eg.getDescription();
            }
        }

        return "";
    }

    public Long getExpenseGroupId() {
        return expenseGroupId;
    }

    public void setExpenseGroupId(Long expenseGroupId) {
        this.expenseGroupId = expenseGroupId;
    }

    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }   
            
    public void updateByPaymentMethod(String newValue) {
    	if (!preserveEditValues) {
    		setBankAccountId(null);
    		setCreditCardId(null);
    		setCheckNumber(null);    	
    		setMoneyOrderId(null);
    	}
    	
    	super.updateByPaymentMethod(newValue);
    }
    
    public void validate() throws Exception {
    	super.validate();
    	if (ListUtils.isNullSelection(expenseTypeId)) {
            throw new Exception("Expense Type is a required field");            
        }
        
        if (expensePurpose == null) {
        	throw new Exception("Expense Purpose is a required field");            
        }
    }

    public String createIt() throws Exception {
    	if (refund && this.amount.doubleValue() > 0) {
    		this.amount = this.amount.negate();
    	}
    	
        this.serviceLocator.getExpenseTransactionalService().setServiceLocator(serviceLocator);
        try {
            this.serviceLocator.getExpenseTransactionalService().saveExpense(this);
        } catch (Exception ex) {
            throw new Exception("Expense was not created");            
        }
        
        clearAllFields();
        this.setId(null);

        return NavigationResults.EXPENSE_CREATED;
    }
    
        
    public void clearAllFields() {
    	super.clearAllFields();
        this.description = null;
        this.expenseGroupId = null;
        this.expenseTypeId = null;        
        this.expensePurpose = null;
        this.refund = false;
        
    }

    public String doCancel() {
        clearAllFields();
        this.setId(null);
        this.refund = false;
        return NavigationResults.CREATE_EXPENSE_CANCELLED;
    }
    
    public List<SelectItem> getExpensePurposeList() {
    	List<SelectItem> dropList = new ArrayList<SelectItem>();
    	dropList.add(new SelectItem(ListUtils.NULL_CONST, ""));
        dropList.add(new SelectItem(ExpensePurpose.Personal.toInt(), "Personal"));
        dropList.add(new SelectItem(ExpensePurpose.Business.toInt(), "Business"));
        return dropList;
    }
    
    public int getExpensePurposeId() {
        if (expensePurpose != null) {
            return expensePurpose.toInt();
        }
        return ListUtils.NULL_CONST.intValue();
    }

    public void setExpensePurpose(ExpensePurpose expensePurpose) {
        this.expensePurpose = expensePurpose;
    }

    public void setExpensePurposeId(int expensePurposeId) {
    	if (expensePurposeId == ListUtils.NULL_CONST.intValue()) {
    		this.expensePurpose = null;
    	} else {
    		this.expensePurpose = ExpensePurpose.fromInt(expensePurposeId);
    	}
    }
	
	public String getFollowOnAction() {
		return NavigationResults.CREATE_EXPENSE_REQUESTED;
	}
	
	@Override
	public String getEditTitle() {
		return "Edit Expense";
	}

	@Override
	public String getNewTitle() {
		return "New Expense";
	}

}
