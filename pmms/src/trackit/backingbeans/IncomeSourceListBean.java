/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import trackit.businessobjects.IncomeSource;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */
public class IncomeSourceListBean extends ListBean {
    
    public List<DescriptionItem> getIncomeSources() {
        List<IncomeSource> incomeSources =
            this.serviceLocator.getIncomeService().getAllIncomeSourcesForAccount(
                FacesUtils.getSessionBean().getCurrentAccountId());
        List<DescriptionItem> list = new ArrayList<DescriptionItem>();
        for (IncomeSource ba : incomeSources) {            
        	DescriptionItem item = new DescriptionItem();
        	item.setId(ba.getId());
        	item.setDescription(ba.getName());
            list.add(item);
            
        }
        
        Collections.sort(list, new DescriptionItemComparator());

        return list;

    }

    
    public String newAction() {
        return NavigationResults.CREATE_INCOME_SOURCE_REQUESTED;
    }

    public String homeAction() {
        return NavigationResults.HOME;
    }


	@Override
	public void deleteItem(IListItem item) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void editItem(IListItem item) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getEditNavigationId() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getListNavigaionId() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
