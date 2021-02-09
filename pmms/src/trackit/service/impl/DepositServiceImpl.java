/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trackit.service.impl;

import java.util.Date;
import java.util.List;
import trackit.businessobjects.Deposit;
import trackit.dao.DepositDao;
import trackit.service.DepositService;

/**
 *
 * @author Owner
 */
public class DepositServiceImpl implements DepositService {

    private DepositDao depositDao;

    public void setDepositDao(DepositDao depositDao) {
        this.depositDao = depositDao;
    }

    public Deposit saveDeposit(Deposit deposit) {
        return depositDao.saveDeposit(deposit);
    }

    public Deposit findDeposit(String id) {
        return depositDao.getDeposit(id);
    }

    public List<Deposit> getAllDepositsForAccount(String accountId) {
        return depositDao.getAllDepositsForAccount(accountId);
    }

    public List<Deposit> getAllDepositsForBankAccount(String bankAccountId, Date fromDate, Date toDate) {
        return depositDao.getAllDepositsForBankAccount(bankAccountId, fromDate, toDate);
    }

	@Override
	public Deposit findDepositByCurrencyOnHandId(String id) {
		return depositDao.findDepositByCurrencyOnHandId(id);
	}

	@Override
	public void deleteDeposit(String id) {
		depositDao.deleteDeposit(id);
		
	}
}
