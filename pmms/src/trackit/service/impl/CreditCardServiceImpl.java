/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trackit.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import trackit.businessobjects.BankAccountTransaction;
import trackit.businessobjects.CreditCard;
import trackit.businessobjects.CreditCardPayment;
import trackit.businessobjects.CreditCardTransaction;
import trackit.dao.CreditCardDao;
import trackit.dao.CreditCardTransactionDao;
import trackit.service.CreditCardService;

/**
 *
 * @author Owner
 */
public class CreditCardServiceImpl implements CreditCardService {

    private CreditCardDao dao;
    private CreditCardTransactionDao transactionDao;

    public void setCreditCardDao(CreditCardDao dao) {
        this.dao = dao;
    }

    public void setCreditCardTransactionDao(CreditCardTransactionDao dao) {
        this.transactionDao = dao;
    }

    public CreditCard saveCreditCard(CreditCard a) {
        CreditCard savedBankAccount = dao.saveCreditCard(a);
        return savedBankAccount;
    }

    public CreditCard findCreditCard(String id) {
        return dao.getCreditCard(id);
    }

    public CreditCard findCreditCardByDescription(String description) {
        try {
            List list = this.dao.findCreditCardByDescription(description);
            if (list.size() == 0) {
                return null;
            }

            return (CreditCard) list.get(0);
        } catch (HibernateObjectRetrievalFailureException e) {
            return null;
        }
    }

    public CreditCardTransaction saveCreditCardTransaction(
            CreditCardTransaction accnt) {
        CreditCardTransaction savedTransaction =
                transactionDao.saveCreditCardTransaction(accnt);
        return savedTransaction;
    }

    public CreditCardTransaction findCreditCardTransaction(String id) {
        return transactionDao.getCreditCardTransaction(id);
    }

    public List<CreditCard> getAllCreditCardsForAccount(String accountId) {
        return dao.getAllCreditCardsForAccount(accountId);
    }

    public CreditCardPayment findCreditCardPayment(String id) {
        return transactionDao.getCreditCardPayment(id);
    }

    public CreditCardPayment saveCreditCardPayment(CreditCardPayment payment) {
        return transactionDao.saveCreditCardPayment(payment);
    }

    public List<CreditCardTransaction> getAllCreditCardTransactionsForCreditCard(String creditCardId, Date fromDate, Date toDate) {
        return transactionDao.getAllCreditCardTransactionsForCreditCard(creditCardId, fromDate, toDate);
    }
    
    public void deleteCreditCard(String id) {
    	dao.deleteCreditCard(id);
    }
    
    
    public int getNumberOfExpensesWithCreditCard(String id) {
		return dao.getNumberOfExpensesWithCreditCard(id);		
	}
	
	public int getNumberOfLoansWithCreditCard(String id) {
		return dao.getNumberOfLoansWithCreditCard(id);
	}
	
	public int getNumberOfPaymentsOnCreditCard(String id) {
		return dao.getNumberOfPaymentsOnCreditCard(id);
	}
	
	public int getNumberOfTransactionsWithCreditCard(String id) {
		return dao.getNumberOfNonOpeningBalanceTransactionsWithCreditCard(id);
	}

	@Override
	public void deleteCreditCardTransaction(String id) {
		transactionDao.deleteTransaction(id);
	}
	
	 public void deleteAllOpeningBalanceTransactions(String creditCardId) {
		 transactionDao.deleteAllOpeningBalanceTransactions(creditCardId);
	 }
	 
	 public BigDecimal getOpeningBalance(String creditCardId) {
		 return transactionDao.getOpeningBalance(creditCardId);
	 }

	@Override
	public CreditCardTransaction findCreditCardOpeningBalanceTransaction(
			String id) {
		return transactionDao.findCreditCardOpeningBalanceTransaction(id);
	}
}
