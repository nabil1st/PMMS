/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import trackit.businessobjects.Payee;
import trackit.service.impl.DataInUseException;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */
public class PayeeListBean extends ListBean {    
	
	private boolean scrollBarVisible = false;
	private List<DescriptionItem> payeeList;
	
    public List<DescriptionItem> getPayees() {
    	if (payeeList == null) {
    		setupData();
    		updateScrollBarVisibility();
    	}
    	
    	return payeeList;
    }
            
    private void setupData() {
    	List<Payee> payees =
            this.serviceLocator.getPayeeService().getAllPayeesForAccount(
                FacesUtils.getSessionBean().getCurrentAccountId());
        
        List<DescriptionItem> list = new ArrayList<DescriptionItem>();
        for (Payee ba : payees) {
            
        	DescriptionItem item = new DescriptionItem();
        item.setId(ba.getId());
        item.setDescription(ba.getDescription());
        list.add(item);
            
        }
        
        Collections.sort(list, new DescriptionItemComparator());        
        this.payeeList = list;
    }
    
    private void updateScrollBarVisibility() {
    	scrollBarVisible = payeeList.size() > 10;
    }
    
    public String newAction() {
    	payeeList = null;
        return NavigationResults.CREATE_PAYEE_REQUESTED;
    }

	@Override
	public void deleteItem(IListItem item) throws DataInUseException {
		this.serviceLocator.getPayeeTransactionalService().setServiceLocator(this.serviceLocator);
		this.serviceLocator.getPayeeTransactionalService().deletePayee(String.valueOf(item.getId()));				
	}

	@Override
	public void editItem(IListItem item) {
		Payee p = this.serviceLocator.getPayeeService().findPayee(
				String.valueOf(item.getId()));
		
		PayeeBean eb = (PayeeBean) FacesUtils.getManagedBean("payeeBean");			
		if (eb != null) {
			eb.setServiceLocator(this.getServiceLocator());
			eb.edit(p);
		}
		
	}

	@Override
	public String getEditNavigationId() {
		return NavigationResults.CREATE_PAYEE_REQUESTED;
	}

	@Override
	public String getListNavigaionId() {
		return NavigationResults.PAYEES;
	}

	@Override
	public void reset() {
		payeeList = null;
		FacesUtils.getSessionBean().resetPayeeInfo();
	}

}
