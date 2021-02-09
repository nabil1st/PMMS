/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trackit.service.impl;

import java.util.Date;
import java.util.List;

import trackit.businessobjects.CashTransaction;
import trackit.businessobjects.CashTransfer;
import trackit.dao.CashTransactionDao;
import trackit.service.CashService;

/**
 *
 * @author Owner
 */
public class CashServiceImpl implements CashService {

    private CashTransactionDao transactionDao;

    public void setCashTransactionDao(CashTransactionDao dao) {
        this.transactionDao = dao;
    }

    public CashTransaction saveCashTransaction(
            CashTransaction accnt) {
        CashTransaction savedTransaction =
                transactionDao.saveCashTransaction(accnt);
        return savedTransaction;
    }

    public CashTransaction findCashTransaction(String id) {
        return transactionDao.getCashTransaction(id);
    }

	@Override
	public CashTransfer findCashTransfer(String id) {
		return transactionDao.getCashTransfer(id);
	}

	@Override
	public List<CashTransfer> getCashTransfers(String accountId, Date fromDate,
			Date toDate) {
		return transactionDao.getCashTransfers(accountId, fromDate, toDate);
	}

	@Override
	public CashTransfer saveCashTransfer(CashTransfer transfer) {
		return transactionDao.saveCashTransfer(transfer);
	}

	@Override
	public void deleteCashTransaction(String id) {
		transactionDao.deleteCashTransaction(id);
		
	}
}
