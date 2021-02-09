/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import trackit.businessobjects.Borrower;
import trackit.businessobjects.Loan;
import trackit.util.DateFormatUtil;
import trackit.util.FacesUtils;

/**
 * A loanSummary bean that will only bring back loans with balance > 0.
 * @author Owner
 */
public class LoanReportListBean extends ListBean {
    private List<LoanSummaryItem> statementItems;
    private BigDecimal total = new BigDecimal(0);
        
    
    public LoanReportListBean() {        
    }


    public List<LoanSummaryItem> getLoans() {
        if (statementItems == null) {
        	initializeData();
        }
        return statementItems;
    }
    
    public BigDecimal getTotal() {
    	if (statementItems == null) {
    		initializeData();    		
    	}
    	
    	return total;
    }
    
    private void initializeData() {
    	String currentAccountId = FacesUtils.getSessionBean().getCurrentAccountId();

        List<Borrower> borrowers = this.serviceLocator.getBorrowerService().getAllBorrowersForAccount(
                currentAccountId);

        Map<Long,String> borrowerLookupMap = new HashMap<Long,String>();
        for (Borrower p : borrowers) {
            borrowerLookupMap.put(p.getId(), p.getName());
        }        


        statementItems = new ArrayList<LoanSummaryItem>();

        List<Loan> loans = this.serviceLocator.getLoanService().getAllLoansForAccount(
                    currentAccountId);
        
        for (Loan e : loans) {
        	BigDecimal balance = LoanUtils.getLoanBalance(e, serviceLocator);
        	
        	if(balance.doubleValue() <= 0) {
        		continue;
        	}
        	
            LoanSummaryItem si = new LoanSummaryItem();
            si.setId(e.getId());
            si.setDate(e.getDate());
            si.setDateStr(DateFormatUtil.formatDate(e.getDate()));
            si.setBorrowerName(borrowerLookupMap.get(e.getBorrowerId()));
            si.setAmount(e.getAmount());
            si.setBalance(balance);
            si.setDescription(e.getDescription());
            statementItems.add(si);
            
            total = total.add(si.getBalance());
        }

        
        Collections.sort(statementItems, new DatedItemComparator());
    }

    public void loanHistoryAction(ActionEvent evt) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        String idstr = (String) ctx.getExternalContext().getRequestParameterMap().get("modelRow");
        FacesUtils.getSessionBean().setCurrentLoanId(idstr);
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
