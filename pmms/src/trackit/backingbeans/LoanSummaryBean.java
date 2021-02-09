/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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
 *
 * @author Owner
 */
public class LoanSummaryBean extends ListBean {
    private List<LoanSummaryItem> statementItems;

    private Date toDate;
    private Date fromDate;
    private boolean showAll;

    public LoanSummaryBean() {
        toDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(toDate);
        c.add(Calendar.DAY_OF_MONTH, -60);
        fromDate = c.getTime();
        showAll = true;
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

    public boolean isShowAll() {
        return showAll;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }



    public List<LoanSummaryItem> getLoans() {


        //if (statementItems == null) {

            // Use the payee name in the description field. Need to lookup
            // payee name by payeeId.
            // therefore, load list of payees in advance
        String currentAccountId = FacesUtils.getSessionBean().getCurrentAccountId();

        List<Borrower> borrowers = this.serviceLocator.getBorrowerService().getAllBorrowersForAccount(
                currentAccountId);

        Map<Long,String> borrowerLookupMap = new HashMap<Long,String>();
        for (Borrower p : borrowers) {
            borrowerLookupMap.put(p.getId(), p.getName());
        }        


        statementItems = new ArrayList<LoanSummaryItem>();

        List<Loan> loans = null;

        if (!showAll) {
            Calendar c = Calendar.getInstance();

            c.setTime(toDate);
            c.add(Calendar.DAY_OF_MONTH, 1);
            Date to_date = c.getTime();

            loans = this.serviceLocator.getLoanService().getLoansForAccount(
                currentAccountId, fromDate, to_date);
        } else {
            loans = this.serviceLocator.getLoanService().getAllLoansForAccount(
                    currentAccountId);
        }

        for (Loan e : loans) {
            LoanSummaryItem si = new LoanSummaryItem();
            si.setId(e.getId());
            si.setDate(e.getDate());
            si.setDateStr(DateFormatUtil.formatDate(e.getDate()));
            si.setBorrowerName(borrowerLookupMap.get(e.getBorrowerId()));
            si.setAmount(e.getAmount());

            BigDecimal balance = LoanUtils.getLoanBalance(e, serviceLocator);
            si.setBalance(balance);
            si.setDescription(e.getDescription());
            statementItems.add(si);
        }

        
        Collections.sort(statementItems, new DatedItemComparator());
        return statementItems;
    }

    public void setLoans(List<LoanSummaryItem> statementItems) {
        this.statementItems = statementItems;
    }

    public String homeAction() {
        return NavigationResults.HOME;
    }    

    public String newLoanAction() {
        return NavigationResults.CREATE_LOAN_REQUESTED;
    }

    public String borrowersAction() {
        return NavigationResults.SHOW_BORROWERS;
    }

    public void loanHistoryAction(ActionEvent evt) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        String idstr = (String) ctx.getExternalContext().getRequestParameterMap().get("modelRow");
        //int id = Integer.parseInt(idstr);
        //BankAccountBean bean = bankAccounts.get(modelRow);
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
