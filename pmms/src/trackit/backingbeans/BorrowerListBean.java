/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import trackit.builder.BorrowerBuilder;
import trackit.businessobjects.Borrower;
import trackit.exception.EntityException;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */
public class BorrowerListBean extends ListBean {


    public List<DescriptionItem> getBorrowers() {
        List<Borrower> Borrowers =
            this.serviceLocator.getBorrowerService().getAllBorrowersForAccount(
                FacesUtils.getSessionBean().getCurrentAccountId());
        List<DescriptionItem> list = new ArrayList<DescriptionItem>();
        for (Borrower ba : Borrowers) {            
        	DescriptionItem item = new DescriptionItem();
        	item.setId(ba.getId());
        	item.setDescription(ba.getName());
            list.add(item);            
        }
        
        Collections.sort(list, new DescriptionItemComparator());

        return list;

    }

    public String newBorrowerAction() {
        return NavigationResults.CREATE_BORROWER_REQUESTED;
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
