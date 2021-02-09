/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import trackit.businessobjects.CashTransfer;
import trackit.businessobjects.Expense;
import trackit.businessobjects.User;
import trackit.util.DateFormatUtil;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

/**
 *
 * @author Nabil
 */
public class CashTransferListBean extends ListBean {
    private List<CashTransferBean> cashTransferList;
    private Date fromDate;
    private Date toDate;

        
    public CashTransferListBean() {
        toDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(toDate);
        c.add(Calendar.DAY_OF_MONTH, -30);
        fromDate = c.getTime();
    }

    public List<CashTransferItem> getCashTransferList() {
        
        List<CashTransfer> transfers =
            this.serviceLocator.getCashService().getCashTransfers(
            		FacesUtils.getSessionBean().getCurrentAccountId(), fromDate, toDate);
        List<CashTransferItem> transferItems = new ArrayList<CashTransferItem>();

        if (transfers.size() > 0) {
            Map<Long,User> userLookup =
                    ListUtils.getUserLookup(serviceLocator);
            for (CashTransfer ba : transfers) {

                CashTransferItem item = new CashTransferItem();

                item.setId(ba.getId());
                item.setDateStr(DateFormatUtil.formatDate(ba.getTransferDate()));
                item.setDate(ba.getTransferDate());
                item.setAmount(ba.getAmount());
                item.setNotes(ba.getNotes());
                if (ba.getSourceId() == null) {
                	item.setSourceAccountName("Main Cash Account");	
                } else {
                	User usr = userLookup.get(ba.getSourceId());
                	item.setSourceAccountName(usr.getFirstName() + " " + usr.getLastName());
                }
                
                if (ba.getDestinationId() == null) {
                	item.setDestinationAccountName("Main Cash Account");
                } else {
                	User usr = userLookup.get(ba.getDestinationId());
                	item.setDestinationAccountName(usr.getFirstName() + " " + usr.getLastName());
                }

                transferItems.add(item);

            }
        }


        Collections.sort(transferItems, new DatedItemComparator());
        return transferItems;
    }
    
    public String newAction() {
        return NavigationResults.TRANSFER_CASH_REQUESTED;
    }

    public String homeAction() {
        return NavigationResults.HOME;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
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
