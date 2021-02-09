/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trackit.service.impl;

import java.util.Date;
import java.util.List;
import trackit.businessobjects.CurrencyOnHand;
import trackit.businessobjects.CurrencyOnHandSourceType;
import trackit.businessobjects.CurrencyOnHandStatus;
import trackit.businessobjects.CurrencyOnHandType;
import trackit.dao.CurrencyOnHandDao;
import trackit.service.CurrencyOnHandService;

/**
 *
 * @author Owner
 */
public class CurrencyOnHandServiceImpl implements CurrencyOnHandService {

    private CurrencyOnHandDao currencyOnHandDao;

    public void setCurrencyOnHandDao(CurrencyOnHandDao incomeDao) {
        this.currencyOnHandDao = incomeDao;
    }

    public CurrencyOnHand saveCurrencyOnHand(CurrencyOnHand source) {
        return currencyOnHandDao.saveCurrencyOnHand(source);
    }

    public CurrencyOnHand findCurrencyOnHand(String id) {
        return currencyOnHandDao.getCurrencyOnHand(id);
    }

    public List<CurrencyOnHand> getAllCurrencyOnHandsForAccount(String accountId) {
        return currencyOnHandDao.getAllCurrencyOnHandsForAccount(accountId);
    }

    public List<CurrencyOnHand> getAllUnusedCurrenciesOnHandForAccount(String currentAccountId) {
        return currencyOnHandDao.getAllUnusedCurrenciesOnHandForAccount(currentAccountId);
    }
        
    public List<CurrencyOnHand> getAllUnusedMoneyOrdersOnHandForAccount(String currentAccountId) {
        return currencyOnHandDao.getAllUnusedMoneyOrdersOnHandForAccount(currentAccountId);
    }

    public List<CurrencyOnHand> getAllLoanPaymentsForAccount(String accountId) {
        return currencyOnHandDao.getAllLoanPaymentsForAccount(accountId);
    }

    public List<CurrencyOnHand> getAllLoanPaymentsForLoan(String loanId) {
        return currencyOnHandDao.getAllLoanPaymentsForLoan(loanId);
    }

    public List<CurrencyOnHand> getCurrencyOnHand(
            String currentAccountId,
            Date toDate,
            Date fromDate,
            boolean showAllDates,
            CurrencyOnHandStatus status,
            CurrencyOnHandType type,
            CurrencyOnHandSourceType sourceType,
            Long sourceId,
            Long groupId) {
        
        return currencyOnHandDao.getCurrencyOnHand(currentAccountId,
                toDate,
                fromDate,
                showAllDates,
                status,
                type,
                sourceType,
                sourceId,
                groupId);
    }

    public List<CurrencyOnHand> getCurrencyOnHandForExpenseGroup(String groupId) {
        return currencyOnHandDao.getCurrencyOnHandForExpenseGroup(groupId);
    }

	@Override
	public void deleteCurrencyOnHand(String id) {
		currencyOnHandDao.deleteCurrencyOnHand(id);
		
	}
}
