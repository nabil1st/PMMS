/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import trackit.businessobjects.Entity;
import trackit.businessobjects.Expense;
import trackit.businessobjects.ExpenseItem;
import trackit.businessobjects.ExpensePurpose;
import trackit.exception.EntityException;
import trackit.service.impl.DataInUseException;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

/**
 *
 * @author Owner
 */
public final class ItemizedExpenseBean extends GenericExpenseBean {

	
    private boolean renderBorrower;
    
    private BigDecimal itemAmount;    
    private ExpensePurpose expensePurpose;
    private Long borrowerId;
    private String itemDescription;
    
    private Long expenseTypeId;
    private String expenseType;
    private Long expenseGroupId;
    
    private BigDecimal tax1;
    private BigDecimal tax2;
    private BigDecimal tax3;
    
    private String[] selectedTaxes = new String[]{"0"};
    
    private BigDecimal total = new BigDecimal(0);
    
    private List<ExpenseItem> expenseItems = new ArrayList<ExpenseItem>();
    
    private ExpenseItem itemToDelete;
    
    private boolean itemListModified = false;
    
    private int scrollerPage;    
    private int pageSize = 10;
    
    public ItemizedExpenseBean(){}
    
    public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
    
    public List<SelectItem> getBorrowerList() {
    	if (this.serviceLocator == null) {
            this.serviceLocator = FacesUtils.getSessionBean().getServiceLocator();
        }
    	return ListUtils.getBorrowersList(serviceLocator, true);
    }
    
    public void updateByExpensePurpose(ValueChangeEvent  e) {
        if (e.getNewValue().toString().equals(String.valueOf(ExpensePurpose.Loan.toInt()))) {
            renderBorrower = true;            
        } else {
        	renderBorrower = false;
        }
    }
    
    @Override
    public void validate() throws Exception {
    	super.validate();
    	
    	for (ExpenseItem ei : expenseItems) {
    		if (ei.getAmount() == null || ei.getAmount().doubleValue() == 0) {
    			throw new Exception("An expense item's amount cannot be 0");                
    		}
    		
    		if (ei.getExpenseTypeId() == null || ei.getExpenseTypeId() < 0) {
    			throw new Exception("Each expense item needs an expense type");                
    		}
    	}
    	
    	
    	for (ExpenseItem ei : expenseItems) {
    		if (ei.getTax() == null) {
    			ei.setTax(new BigDecimal(0));
    		}
    	}
    	
    	BigDecimal calculatedTotal = new BigDecimal(0);
    	for (ExpenseItem ei : expenseItems) {
    		calculatedTotal = calculatedTotal.add(ei.getAmount());
    		calculatedTotal = calculatedTotal.add(ei.getTax());
    	}
    	
    	if (calculatedTotal.doubleValue() != getAmount().doubleValue()) {
    		throw new Exception("Total expense amount is not equal to the sum of the expense item amounts and taxes. Please revise");            
    	}
    }

    @Override
    public String createIt() throws Exception {

        this.serviceLocator.getExpenseTransactionalService().setServiceLocator(serviceLocator);
        try {
            this.serviceLocator.getExpenseTransactionalService().saveItemizedExpense(this);
        } catch (DataInUseException de) {
        	throw de;
        } catch (Exception ex) {
            throw new Exception("Expense was not created");            
        }
        
        clearAllFields();
        this.setId(null);

        return NavigationResults.ITEMIZE_EXPENSE_CREATED;
    }
    
    public String addExpenseItemAction() {
    	if (itemAmount.doubleValue() == 0) {
    		FacesUtils.addErrorMessage("Item's amount cannot be 0");
    		return "";
    	}
    	
    	if (ListUtils.isNullSelection(expenseTypeId)) {
    		FacesUtils.addErrorMessage("An Expense Type must be selected for loan items.");
    		return "";
    	}
    	
    	if (expensePurpose == null) {
    		FacesUtils.addErrorMessage("A Expense Purpose must be selected for loan items.");
    		return "";
    	}
    	
    	if (expensePurpose.equals(ExpensePurpose.Loan) && (borrowerId == null || ListUtils.isNullSelection(borrowerId))) {
    		FacesUtils.addErrorMessage("A borrower must be selected for loan items.");
    		return "";
    	}
    	    	
    	
    	ExpenseItem item = new ExpenseItem();
    	item.setId(getNewId(expenseItems));
    	setMetadata(item);
    	item.setAmount(itemAmount);    	
    	item.setDescription(itemDescription);
    	item.setExpenseTypeId(expenseTypeId);
    	item.setExpenseGroupId(expenseGroupId);
    	item.setExpensePurpose(expensePurpose);
    	item.setBorrowerId(borrowerId);
    	item.setTax(new BigDecimal(0));
    	
    	for (int i=0; i<selectedTaxes.length; i++) {
    		item.getSelectedTaxes().add(selectedTaxes[i]);
    	}
    	    	
    	expenseItems.add(item);
    	
//    	boolean taxPercent = this.tax1 != null && this.tax1.doubleValue() > 0 ||
//    				this.tax2 != null && this.tax2.doubleValue() > 0 ||
//    				this.tax3 != null && this.tax3.doubleValue() > 0;
//    	boolean taxTotaled = this.totalTax != null && this.totalTax.doubleValue() > 0;
    	
    	BigDecimal totalTax = new BigDecimal(0);
    	if (tax1 != null) {
    		totalTax = totalTax.add(tax1);
    	}
    	if (tax2 != null) {
    		totalTax = totalTax.add(tax2);
    	}
    	if (tax3 != null) {
    		totalTax = totalTax.add(tax3);
    	}
    	
    	
    	if (totalTax.doubleValue() > 0) {
    		// we need to figure out the total taxable amount
    		BigDecimal totalTaxableAmount = new BigDecimal(0);
    		int numberOfTaxableItems = 0;
    		for (ExpenseItem ei : expenseItems) {
        		if (ei.getSelectedTaxes().contains("1") || ei.getTax().doubleValue() > 0) {
        			totalTaxableAmount = totalTaxableAmount.add(ei.getAmount());
        			numberOfTaxableItems++;
        		}
        	}
    		
    		
    		BigDecimal taxPercentage = new BigDecimal(1);
    		if (totalTaxableAmount.doubleValue() > 0) {
    			taxPercentage = totalTax.divide(totalTaxableAmount, 10, RoundingMode.DOWN);
    		}
    		
    		// calculate the total tax for each item
        	total = new BigDecimal(0);
        	int itemIndex = 0;
        	BigDecimal runningTotalTax = new BigDecimal(0);
        	for (ExpenseItem ei : expenseItems) {        		
        		// TODO: selected taxes needs to be saved in the database and reloaded when the item is edited.
        		// TODO: for now just check if the ei has a tax. This will tell us if the item was saved with tax.
        		if (ei.getSelectedTaxes().contains("1") || ei.getTax().doubleValue() > 0) {
        			if (itemIndex < numberOfTaxableItems - 1) {
	        			BigDecimal itemTax = ei.getAmount().multiply(taxPercentage);
	        			itemTax = itemTax.setScale(2, RoundingMode.DOWN);
	        			ei.setTax(itemTax);
	        			runningTotalTax = runningTotalTax.add(itemTax);
        			} else {
        				ei.setTax(totalTax.subtract(runningTotalTax));
        			}
        			
        			itemIndex++;
        		} 		
        		
        		
        		total = total.add(ei.getAmount());
        		if (ei.getTax() != null) {
        			total = total.add(ei.getTax());
        		}
        		
        		total = total.setScale(2, RoundingMode.DOWN);
        	}
    		
    	} else { // No tax on all items
    		total = new BigDecimal(0);
        	for (ExpenseItem ei : expenseItems) {
        		total = total.add(ei.getAmount());        		
        	}
    	}
    	
    	
    	selectedTaxes = new String[]{"0"};   // default selection is No Tax
    	renderBorrower = false;
    	borrowerId = null;
    	itemAmount = null;
    	itemDescription = null;
    	expensePurpose = null;    	
    	expenseGroupId = null;
    	expenseTypeId = null;
    	
    	itemListModified = true;
    	
//    	if (expenseItems.size() > scrollerPage * pageSize) {
//    		scrollerPage++;
//    	}
    	
    	return "";
    }

	private void setMetadata(ExpenseItem item) {
		item.setExpenseGroupLookup(ListUtils.getExpenseGroupLookup(serviceLocator));
    	item.setExpenseTypeLookup(ListUtils.getExpenseTypeLookup(serviceLocator));
    	item.setBorrowerLookup(ListUtils.getBorrowersLookup(serviceLocator));
	}
    
    private Long getNewId(List<ExpenseItem> list) {
    	Long tempId = new Long(-1);
    	for (ExpenseItem ei : list) {
    		if (ei.getId() < 0 && tempId >= ei.getId()) {
    			tempId = ei.getId() - 1;
    		}
    	}
    	
    	return tempId;
    }
    
    @Override
    public void editIt(Entity entity) {
    	Expense expense = (Expense) entity;
    	super.editIt(expense);
    	
    	this.itemListModified = false;
    	
    	BigDecimal totalTax = new BigDecimal(0);
    	BigDecimal total = new BigDecimal(0);
    	
    	// Create and populate the expense items.
    	expenseItems = new ArrayList<ExpenseItem>();
    	
    	for (ExpenseItem ei : expense.getExpenseItems()) {
    		ExpenseItem item = (ExpenseItem)ei.clone();
    		setMetadata(item);
    		expenseItems.add(item);    		
    		totalTax = totalTax.add(ei.getTax());
    		total = total.add(item.getAmount()).add(item.getTax());
    	}
    	
    	this.tax1 = totalTax;
    	this.total = total;
    }

    public void clearAllFields() {
        super.clearAllFields();
        this.itemListModified = false;
        this.renderBorrower = false;
        expenseItems = new ArrayList<ExpenseItem>();
        this.itemAmount = null;
        this.itemDescription = null;
        this.expensePurpose = null;        
        this.total = null;
        this.selectedTaxes = new String[]{"0"};
        this.tax1 = null;
        this.tax2 = null;
        this.tax3 = null;
    }
    
    public String addBorrowerAction() throws EntityException {
        FacesUtils.getSessionBean().setFollowOnAction(
                NavigationResults.ITEMIZE_EXPENSE_REQUESTED);
        return NavigationResults.CREATE_BORROWER_REQUESTED;
    }
    
    public boolean isRenderBorrower() {
    	return renderBorrower;
    }
    
    public void setRenderBorrower(boolean value) {
    	this.renderBorrower = value;
    }

	public BigDecimal getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(BigDecimal itemAmount) {
		this.itemAmount = itemAmount;
	}

	
	public ExpensePurpose getExpensePurpose() {
		return this.expensePurpose;
	}
	
	public String getExpensePurposeName() {
        return expensePurpose.toString();
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
    	if (ListUtils.NULL_CONST.intValue() == expensePurposeId) {
    		this.expensePurpose = null;
    	} else {
    		this.expensePurpose = ExpensePurpose.fromInt(expensePurposeId);
    	}
    }

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public Long getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(Long expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public Long getExpenseGroupId() {
		return expenseGroupId;
	}

	public void setExpenseGroupId(Long expenseGroupId) {
		this.expenseGroupId = expenseGroupId;
	}

	public List<ExpenseItem> getExpenseItems() {
		return expenseItems;
	}

	public void setExpenseItems(List<ExpenseItem> expenseItems) {
		this.expenseItems = expenseItems;
	}
	
	public Long getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(Long borrowerId) {
		this.borrowerId = borrowerId;
	}

	public void deleteAction(ActionEvent evt) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        String idstr = (String) ctx.getExternalContext().getRequestParameterMap().get("modelRow");
        Long id = Long.parseLong(idstr);
        for (ExpenseItem ei : expenseItems) {
        	if (ei.getId().longValue() == id.longValue()) {
        		expenseItems.remove(ei);
        		total = total.subtract(ei.getAmount());
        		total = total.subtract(ei.getTax());
        		break;
        	}
        }
        
        itemListModified = true;
        //this.serviceLocator.getExpenseTransactionalService().setServiceLocator(serviceLocator);
        //this.serviceLocator.getExpenseTransactionalService().deleteExpense(idstr);
        
        
    }
	

	public BigDecimal getTax1() {
		return tax1;
	}

	public void setTax1(BigDecimal tax1) {
		this.tax1 = tax1;
	}

	public BigDecimal getTax2() {
		return tax2;
	}

	public void setTax2(BigDecimal tax2) {
		this.tax2 = tax2;
	}

	public BigDecimal getTax3() {
		return tax3;
	}

	public void setTax3(BigDecimal tax3) {
		this.tax3 = tax3;
	}

	public String[] getSelectedTaxes() {
		return selectedTaxes;
	}

	public void setSelectedTaxes(String[] selectedTaxes) {
		this.selectedTaxes = selectedTaxes;
	}	
	
	public String deleteIt() {
		if (itemToDelete != null) {
			Long id = itemToDelete.getId();
			
			if (id != null && id >= 0) {
				try {
					this.serviceLocator.getExpenseTransactionalService().setServiceLocator(this.serviceLocator);
					this.serviceLocator.getExpenseTransactionalService().verifyExpenseItemCanBeDeleted(String.valueOf(id));
				} catch (DataInUseException e) {
					FacesUtils.addErrorMessage(e.getMessage());
					return "";
				}
			}
			
	        for (ExpenseItem ei : expenseItems) {
	        	if (ei.getId().longValue() == id.longValue()) {
	        		expenseItems.remove(ei);
	        		total = total.subtract(ei.getAmount());
	        		total = total.subtract(ei.getTax());
	        		itemListModified = true;
	        		break;
	        	}
	        }
		}
		
//		if (expenseItems.size() < ((scrollerPage * pageSize) - pageSize + 1 )) {
//			scrollerPage--;
//		}
		
		return "";
	}

	public ExpenseItem getItemToDelete() {
		return itemToDelete;
	}

	public void setItemToDelete(ExpenseItem itemToDelete) {
		this.itemToDelete = itemToDelete;
	}
	
	
	public String getFollowOnAction() {
		return NavigationResults.CREATE_EXPENSE_REQUESTED;
	}
	
	public String doCancel() {
        clearAllFields();
        return NavigationResults.ITEMIZE_EXPENSE_CANCELLED;
    }

	@Override
	public String getEditTitle() {
		return "Edit Itemized Expense";
	}

	@Override
	public String getNewTitle() {
		return "New Itemized Expense";
	}

	public boolean isItemListModified() {
		return itemListModified;
	}

	public void setItemListModified(boolean itemListModified) {
		this.itemListModified = itemListModified;
	}

	public int getScrollerPage() {
		return scrollerPage;
	}

	public void setScrollerPage(int scrollerPage) {
		this.scrollerPage = scrollerPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	

}
