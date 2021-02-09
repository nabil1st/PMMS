/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import trackit.businessobjects.Account;
import trackit.businessobjects.BankAccount;
import trackit.businessobjects.BankTransactionType;
import trackit.dao.BankAccountDao;

/**
 *
 * @author Owner
 */
public class BankAccountDaoHibernateImpl extends HibernateDaoSupport implements BankAccountDao {
    /**
     * Default constructor.
     */
    public BankAccountDaoHibernateImpl() {
            super();
    }

    /**
     * @see UserDao#getUser(String)
     */
    public BankAccount getBankAccount(String id) {
            BankAccount account = (BankAccount)getHibernateTemplate().load(BankAccount.class, Long.parseLong(id));
            return account;
    }

    public BankAccount saveBankAccount(BankAccount account) {
        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        if (account.getId() == null || account.getId() == 0) {
            this.getHibernateTemplate().save(account);
        } else {
            this.getHibernateTemplate().update(account);
        }
        getHibernateTemplate().flush();
        return account;
    }

    public List findBankAccountByDescription(String description) {
        return getHibernateTemplate().find("from BankAccount account where account.description=?", description);
    }

    public List<BankAccount> getAllBankAccountsForAccount(String accountId) {
        List accounts = getHibernateTemplate().find("from Account account where account.id=?", Long.valueOf(accountId));
        Account account = (Account) accounts.get(0);
        if (account.getBankAccounts() != null) {
            return new ArrayList<BankAccount>(account.getBankAccounts());
        } else {
            return new ArrayList<BankAccount>();
        }
    }

	@Override
	public void deleteBankAccount(String id) {
		BankAccount ba = getBankAccount(id);
		getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        getHibernateTemplate().delete(ba);
        getHibernateTemplate().flush();
		
	}
	
	public int getNumberOfExpensesWithBankAccount(String id) {
		String hql="select count(expense) from Expense expense where expense.bankAccountId = " + id;
		return getCountForHQL(hql);		
	}
	
	public int getNumberOfLoansWithBankAccount(String id) {
		String hql="select count(loan) from Loan loan where loan.bankAccountId = " + id;
		return getCountForHQL(hql);
	}
	
	public int getNumberOfDepositsWithBankAccount(String id) {
		String hql="select count(deposit) from Deposit deposit where deposit.bankAccountId = " + id;
		return getCountForHQL(hql);
	}
	
	public int getNumberOfNonOpeningBalanceTransactionsWithBankAccount(String id) {
		String hql="select count(xaction) from BankAccountTransaction xaction where xaction.bankAccountId = " 
			+ id + " and xaction.transactionType <> " + BankTransactionType.OPENING_BALANCE.toInt();
		return getCountForHQL(hql);
	}
	
	private int getCountForHQL(String hql) {
		List result = getHibernateTemplate().find(hql);
		Long count = (Long) result.get(0);
		return count.intValue();
	}
}

