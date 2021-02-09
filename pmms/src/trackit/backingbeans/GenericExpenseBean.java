package trackit.backingbeans;

import java.util.List;

import javax.faces.model.SelectItem;

import trackit.businessobjects.Entity;
import trackit.businessobjects.Expense;
import trackit.exception.EntityException;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

public abstract class GenericExpenseBean extends GenericPaymentEnabledBean {
		    
    private Long payeeId;    
    private Long userId;
    
    
    protected boolean preserveEditValues = false;

    public Long getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(Long payeeId) {
        this.payeeId = payeeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public void editIt(Entity entity) {
    	Expense expense = (Expense) entity;    	
    	this.date = expense.getDate();
    	this.amount = expense.getAmount();
    	this.paymentMethod = expense.getPaymentMethod();
    	this.bankAccountId = expense.getBankAccountId();    	
    	this.checkNumber = expense.getCheckNumber();
    	this.creditCardId = expense.getCreditCardId();
    	this.moneyOrderId = expense.getMoneyOrderId();
    	this.oldMoneyOrderId = this.moneyOrderId;
    	this.payeeId = expense.getPayeeId();
    	this.userId = expense.getUserId();
    	    	
    	preserveEditValues = true;
    	updateByPaymentMethod(String.valueOf(paymentMethod.toInt()));
    	preserveEditValues = false;
    }
    
    public void clearAllFields() {
        super.clearAllFields();
        this.payeeId = null;        
    }

    public String addExpenseGroupAction() throws EntityException {
        FacesUtils.getSessionBean().setFollowOnAction(
        		getFollowOnAction());
        return NavigationResults.CREATE_EXPENSE_GROUP_REQUESTED;
    }

    public String addExpenseTypeAction() throws EntityException {
        FacesUtils.getSessionBean().setFollowOnAction(
        		getFollowOnAction());
        return NavigationResults.CREATE_EXPENSE_TYPE_REQUESTED;
    }

    public String addPayeeAction() throws EntityException {
        FacesUtils.getSessionBean().setFollowOnAction(
        		getFollowOnAction());
        return NavigationResults.CREATE_PAYEE_REQUESTED;
    }
    
    public Long getOldMoneyOrderId() {
		return oldMoneyOrderId;
	}

	public void setOldMoneyOrderId(Long oldMoneyOrderId) {
		this.oldMoneyOrderId = oldMoneyOrderId;
	}
	
	public String clearAction() {
        clearAllFields();
        return "";
    }
	
	

    public List<SelectItem> getExpenseGroupList() {
    	if (this.serviceLocator == null) {
            this.serviceLocator = FacesUtils.getSessionBean().getServiceLocator();
        }
        return ListUtils.getExpenseGroupList(serviceLocator, true);
    }
    
    public List<SelectItem> getExpenseCategoryTypeList() {
    	if (this.serviceLocator == null) {
            this.serviceLocator = FacesUtils.getSessionBean().getServiceLocator();
        }

        return ListUtils.getExpenseCategoryTypeList(serviceLocator);
    }
    
    public List<SelectItem> getExpensePurposeList() {
    	return ListUtils.getExpensePurposeList(serviceLocator);
    }
    
    public List<SelectItem> getPayeeList() {
    	if (this.serviceLocator == null) {
            this.serviceLocator = FacesUtils.getSessionBean().getServiceLocator();
        }
        return ListUtils.getPayeeList(this.serviceLocator, true);
    }
    
    public String getTitle() {
    	if (editMode) {
    		return getEditTitle();
    	} else {
    		return getNewTitle();
    	}
    }
    
    public abstract String getEditTitle();
    public abstract String getNewTitle();
    
    @Override
    public void validate() throws Exception {   
    	
    	super.validate();
        
        if (ListUtils.isNullSelection(getPayeeId())) {
        	throw new Exception("Payee is a required field");
        }
    }
    
    @Override
    public String getListBeanName() {
    	return "expenseListBean";
    }
    

}
