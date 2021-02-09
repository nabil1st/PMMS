/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.service.impl;

import trackit.businessobjects.Account;
import trackit.service.AccountService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import trackit.dao.AccountDao;


/**
 *
 * @author Owner
 */
public class AccountServiceImpl implements AccountService {

    private Log logger = LogFactory.getLog(this.getClass());

	private AccountDao accountDao;

    public void setAccountDao(AccountDao dao) {
        this.accountDao = dao;
    }

    public Account saveAccount(Account account) {
        return accountDao.saveAccount(account);
    }

    public Account findAccount(String accountId) {
        return accountDao.getAccount(accountId);
    }

}
