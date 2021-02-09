/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trackit.service.impl;

import java.util.List;
import trackit.businessobjects.Income;
import trackit.businessobjects.IncomeSource;
import trackit.dao.IncomeDao;
import trackit.dao.IncomeSourceDao;
import trackit.service.IncomeService;

/**
 *
 * @author Owner
 */
public class IncomeServiceImpl implements IncomeService {

    private IncomeDao incomeDao;
    private IncomeSourceDao incomeSourceDao;

    public void setIncomeDao(IncomeDao incomeDao) {
        this.incomeDao = incomeDao;
    }

    public void setIncomeSourceDao(IncomeSourceDao incomeSourceDao) {
        this.incomeSourceDao = incomeSourceDao;
    }

    public IncomeSource saveIncomeSource(IncomeSource source) {
        return incomeSourceDao.saveIncomeSource(source);
    }

    public IncomeSource findIncomeSource(String id) {
        return incomeSourceDao.getIncomeSource(id);
    }

    public List<IncomeSource> getAllIncomeSourcesForAccount(String accountId) {
        return incomeSourceDao.getAllIncomeSourcesForAccount(accountId);
    }

    public Income saveIncome(Income income) {
        return incomeDao.saveIncome(income);
    }

    public Income fineIncome(String id) {
        return incomeDao.getIncome(id);
    }

    public List<Income> getAllUndepositedIncomesForAccount(String accountId) {
        return incomeDao.getAllUndepositedIncomesForAccount(accountId);
    }

}
