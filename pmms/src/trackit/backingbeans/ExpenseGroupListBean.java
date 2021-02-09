/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import trackit.businessobjects.Expense;
import trackit.businessobjects.ExpenseGroup;
import trackit.service.impl.DataInUseException;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */
public class ExpenseGroupListBean extends ListBean {
	
	private List<DescriptionItem> list;
	private boolean scrollBarVisible = false;
	private DescriptionItem itemToDelete;

    public List<DescriptionItem> getExpenseGroups() {
    	if (list == null) {
    		initializeList();
    		updateScrollBarVisibility();
    	}
    	
    	return list;
    }
    
    private void initializeList() {
    	List<ExpenseGroup> expenseGroups =
            this.serviceLocator.getExpenseGroupService().getAllExpenseGroupsForAccount(
                FacesUtils.getSessionBean().getCurrentAccountId());
        list = new ArrayList<DescriptionItem>();
        for (ExpenseGroup ba : expenseGroups) {            
	    	DescriptionItem item = new DescriptionItem();
	    	item.setId(ba.getId());
	    	item.setDescription(ba.getDescription());
	        list.add(item);            
        }
        
        Collections.sort(list, new DescriptionItemComparator());
    }
    
    private void updateScrollBarVisibility() {
    	scrollBarVisible = list.size() > 10;
    }
    

	public String newAction() {
        return NavigationResults.CREATE_EXPENSE_GROUP_REQUESTED;
    }
    
    public String expenseSummaryAction() {
        return NavigationResults.SHOW_EXPENSES;
    }
    
    public void reset() {
    	list = null;
    	FacesUtils.getSessionBean().resetExpenseGroupInfo();
    }

	public void deleteItem(IListItem item) throws DataInUseException {
		this.serviceLocator.getExpenseTransactionalService().setServiceLocator(this.serviceLocator);		
		this.serviceLocator.getExpenseTransactionalService().deleteExpenseGroup(String.valueOf(item.getId()));
	}

	public String getListNavigaionId() {
		return NavigationResults.EXPENSE_GROUPS;
	}

	@Override
	public void editItem(IListItem item) {
		ExpenseGroup eg = this.serviceLocator.getExpenseGroupService().findExpenseGroup(
				String.valueOf(item.getId()));
		
		ExpenseGroupBean eb = (ExpenseGroupBean) FacesUtils.getManagedBean("expenseGroupBean");			
		if (eb != null) {
			eb.setServiceLocator(this.getServiceLocator());
			eb.editExpenseGroup(eg);
		}
		
	}

	public String getEditNavigationId() {
		return NavigationResults.CREATE_EXPENSE_GROUP_REQUESTED;
	}

	

}
