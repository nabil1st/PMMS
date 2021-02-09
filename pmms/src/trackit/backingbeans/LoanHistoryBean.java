/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import trackit.businessobjects.Borrower;
import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.Loan;
import trackit.util.DateFormatUtil;
import trackit.util.FacesUtils;

/**
 *
 * @author Owner
 */
public class LoanHistoryBean extends ListBean {
    private List<LoanHistoryItem> historyItems;
    private String description;
    
    public LoanHistoryBean() {
    }

    public List<LoanHistoryItem> getHistoryItems() {
        
        String currentLoanId = FacesUtils.getSessionBean().getCurrentLoanId();
        
        historyItems = new ArrayList<LoanHistoryItem>();
        
        Loan loan = getLoan();

        LoanHistoryItem loanCreationItem = new LoanHistoryItem();
        loanCreationItem.setDate(loan.getDate());
        loanCreationItem.setDateStr(DateFormatUtil.formatDate(loan.getDate()));
        loanCreationItem.setDescription("Loan Created");
        loanCreationItem.setAmount(loan.getAmount());

        historyItems.add(loanCreationItem);

        List<CurrencyOnHand> loanPayments = 
                this.serviceLocator.getCurrencyOnHandService().getAllLoanPaymentsForLoan(
                currentLoanId);
        
        for (CurrencyOnHand e : loanPayments) {
            LoanHistoryItem si = new LoanHistoryItem();
            si.setDate(e.getDate());
            si.setDateStr(DateFormatUtil.formatDate(e.getDate()));
            si.setAmount(e.getAmount());
            si.setDescription("Payment");
            historyItems.add(si);
        }

        Collections.sort(historyItems, new DatedItemComparator());
        return historyItems;
    }

    public void setHistoryItems(List<LoanHistoryItem> historyItems) {
        this.historyItems = historyItems;
    }

    public String homeAction() {
        return NavigationResults.HOME;
    }

    public String summaryAction() {
        return NavigationResults.SHOW_LOAN_SUMMARY;
    }

    public String paymentAction() {
        return NavigationResults.CREATE_LOAN_PAYMENT_REQUESTED;
    }   
    

    private Loan getLoan() {
        String currentLoanId = FacesUtils.getSessionBean().getCurrentLoanId();
        Loan loan =  this.serviceLocator.getLoanService().getLoan(currentLoanId);
        if (loan.getDescription() != null && loan.getDescription().trim().length() > 0) {
        	this.description = loan.getDescription() + " " + 
        	DateFormatUtil.formatDate(loan.getDate());
        } else {
        	Borrower borrower = this.serviceLocator.getBorrowerService().findBorrower(
        			loan.getBorrowerId().toString());
        	 
            StringBuffer buff = new StringBuffer();
            buff.append(borrower.getName()).
                    append("-").
                    append(DateFormatUtil.formatDate(loan.getDate())).
                    append(" (").
                    append(loan.getAmount()).
                    append(")");
            this.description = buff.toString();
        }
        
        return loan;
    }

    public BigDecimal getBalance() {
        return LoanUtils.getLoanBalance(getLoan(), serviceLocator);
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
