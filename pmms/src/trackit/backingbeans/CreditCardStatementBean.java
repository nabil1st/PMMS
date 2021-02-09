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
import java.util.List;
import java.util.Map;
import trackit.businessobjects.CreditCard;
import trackit.businessobjects.CreditCardTransaction;
import trackit.businessobjects.CreditCardTransactionType;
import trackit.businessobjects.Expense;
import trackit.businessobjects.TransactionReason;
import trackit.util.DateFormatUtil;
import trackit.util.FacesUtils;
import trackit.util.ListUtils;

/**
 *
 * @author Owner
 */
public class CreditCardStatementBean extends ListBean {
    private List<CreditCardStatementItem> statementItems;

    private Date toDate;
    private Date fromDate;

    public CreditCardStatementBean() {
        toDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(toDate);
        c.add(Calendar.DAY_OF_MONTH, -60);
        fromDate = c.getTime();
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


    public List<CreditCardStatementItem> getStatementItems() {
        
        Calendar c = Calendar.getInstance();

        c.setTime(toDate);
        c.add(Calendar.DAY_OF_MONTH, 1);

        Date to_date = c.getTime();


        return statementItems = ListUtils.getCreditCardTransactions(fromDate, to_date, this.serviceLocator);        
    }

    public void setStatementItems(List<CreditCardStatementItem> statementItems) {
        this.statementItems = statementItems;
    }

    public String homeAction() {
        return NavigationResults.HOME;
    }

    public String summaryAction() {
        return NavigationResults.CREDIT_CARD_SUMMARY;
    }

    public String paymentAction() {
        return NavigationResults.CREDIT_CARD_PAYMENT;
    }

    public String chargesCreditsAction() {
        return NavigationResults.CREDIT_CARD_TRANSACTION;
    }
    
    public String compareAction() {
        return NavigationResults.UPLOAD_STATEMENT;
    }

    public BigDecimal getCreditCardBalance() {
        return getCreditCard().getBalance();
    }

    public String getCreditCardName(){
        return getCreditCard().getDescription();
    }

    private CreditCard getCreditCard() {
//        if (this.bankAccount == null ||
//            !this.bankAccount.getId().toString().equals(FacesUtils.getSessionBean().getCurrentBankAccountId())) {
            CreditCard account = this.serviceLocator.getCreditCardService().findCreditCard(
                FacesUtils.getSessionBean().getCurrentCreditCardId());
            return account;
//        } else {
//            return this.bankAccount;
//        }
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
