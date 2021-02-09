/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trackit.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import trackit.businessobjects.BankAccount;
import trackit.businessobjects.BankAccountTransaction;
import trackit.dao.BankAccountDao;
import trackit.dao.BankAccountTransactionDao;
import trackit.service.BankAccountService;

/**
 *
 * @author Owner
 */
public class BankAccountServiceImpl implements BankAccountService {

    private BankAccountDao dao;
    private BankAccountTransactionDao transactionDao;

    public void setBankAccountDao(BankAccountDao dao) {
        this.dao = dao;
    }

    public void setBankAccountTransactionDao(BankAccountTransactionDao dao) {
        this.transactionDao = dao;
    }

    public BankAccount saveBankAccount(BankAccount a) {
        BankAccount savedBankAccount = dao.saveBankAccount(a);
        return savedBankAccount;
    }

    public BankAccount findBankAccount(String id) {
        return dao.getBankAccount(id);
    }

    public BankAccount findBankAccountByDescription(String description) {
        try {
            List list = this.dao.findBankAccountByDescription(description);
            if (list.size() == 0) {
                return null;
            }

            return (BankAccount) list.get(0);
        } catch (HibernateObjectRetrievalFailureException e) {
            return null;
        }
    }

    public BankAccountTransaction saveBankAccountTransaction(BankAccountTransaction accnt) {
        BankAccountTransaction savedTransaction =
                transactionDao.saveBankAccountTransaction(accnt);
        return savedTransaction;
    }

    public BankAccountTransaction findBankAccountTransaction(String id) {
        return transactionDao.getBankAccountTransaction(id);
    }

    public List<BankAccount> getAllBankAccountsForAccount(String accountId) {
        return dao.getAllBankAccountsForAccount(accountId);
    }

    public List<BankAccountTransaction> getBankAccountTransactionsForAccount(String accountId, Date fromDate, Date toDate) {
        return transactionDao.getAllBankAccountTransactions(accountId, fromDate, toDate);
    }

	@Override
	public void deleteBankAccount(String id) {
		dao.deleteBankAccount(id);
	}
	
	public int getNumberOfExpensesWithBankAccount(String id) {
		return dao.getNumberOfExpensesWithBankAccount(id);		
	}
	
	public int getNumberOfLoansWithBankAccount(String id) {
		return dao.getNumberOfLoansWithBankAccount(id);
	}
	
	public int getNumberOfDepositsWithBankAccount(String id) {
		return dao.getNumberOfDepositsWithBankAccount(id);
	}
	
	public int getNumberOfTransactionsWithBankAccount(String id) {
		return dao.getNumberOfNonOpeningBalanceTransactionsWithBankAccount(id);
	}

	@Override
	public void deleteBankAccountTransaction(String id) {
		transactionDao.deleteTransaction(id);
	}
	
	 public void deleteAllOpeningBalanceTransactions(String bankAccountId) {
		 transactionDao.deleteAllOpeningBalanceTransactions(bankAccountId);
	 }
	 
	 public BigDecimal getOpeningBalance(String bankAccountId) {
		 return transactionDao.getOpeningBalance(bankAccountId);
	 }

	@Override
	public BankAccountTransaction findBankAccountOpeningBalanceTransaction(
			String id) {
		return transactionDao.findBankAccountOpeningBalanceTransaction(id);
	}
}
