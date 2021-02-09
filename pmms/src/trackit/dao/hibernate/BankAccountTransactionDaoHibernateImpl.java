/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.dao.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import trackit.businessobjects.BankAccountTransaction;
import trackit.businessobjects.BankTransactionType;
import trackit.dao.BankAccountTransactionDao;

/**
 *
 * @author Owner
 */
public class BankAccountTransactionDaoHibernateImpl extends HibernateDaoSupport implements BankAccountTransactionDao {
    /**
     * Default constructor.
     */
    public BankAccountTransactionDaoHibernateImpl() {
            super();
    }

    /**
     * @see UserDao#getUser(String)
     */
    public BankAccountTransaction getBankAccountTransaction(String id) {
            BankAccountTransaction account =
                    (BankAccountTransaction)getHibernateTemplate().load(BankAccountTransaction.class, Long.parseLong(id));
            return account;
    }

    public BankAccountTransaction saveBankAccountTransaction(BankAccountTransaction xaction) {

        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        if (xaction.getId() == null || xaction.getId() == 0) {
            this.getHibernateTemplate().save(xaction);
        } else {
            this.getHibernateTemplate().update(xaction);
        }
        getHibernateTemplate().flush();
        return xaction;
    }

    public List<BankAccountTransaction> getAllBankAccountTransactions(
            String accountId, Date fromDate, Date toDate) {
        //List<Long> userIds = Utils.getUserIdsForAccountId(getHibernateTemplate(), accountId);

        String[] paramNames = new String[]{"bankAccountId", "fromDate", "toDate"};
        Object[] values = new Object[]{Long.parseLong(accountId), fromDate, toDate};
        List transactions = getHibernateTemplate().findByNamedParam(
                "from BankAccountTransaction xaction where xaction.bankAccountId = :bankAccountId and xaction.date >= :fromDate and xaction.date < :toDate order by xaction.date", paramNames, values);
        return new ArrayList<BankAccountTransaction>(transactions);
    }
    
    public void deleteAllOpeningBalanceTransactions(String bankAccountId) {
        String[] paramNames = new String[]{"bankAccountId", "transactionType"};
        
        Object[] values = new Object[]{Long.parseLong(bankAccountId), BankTransactionType.OPENING_BALANCE};
        List transactions = getHibernateTemplate().findByNamedParam(
                "from BankAccountTransaction xaction where xaction.bankAccountId = :bankAccountId and xaction.transactionType = :transactionType", paramNames, values);
        
        if (transactions != null) {
        	for (int i=0; i<transactions.size(); i++) {
        		BankAccountTransaction xaction = (BankAccountTransaction) transactions.get(i);
        		getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
                getHibernateTemplate().delete(xaction);
                getHibernateTemplate().flush();
        	}
        }
    }

	@Override
	public void deleteTransaction(String id) {
		BankAccountTransaction xaction = getBankAccountTransaction(id);
		getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        getHibernateTemplate().delete(xaction);
        getHibernateTemplate().flush();
	}
	
	public BigDecimal getOpeningBalance(String bankAccountId) {
		String[] paramNames = new String[]{"bankAccountId", "transactionType"};
        
        Object[] values = new Object[]{Long.parseLong(bankAccountId), BankTransactionType.OPENING_BALANCE};
        List transactions = getHibernateTemplate().findByNamedParam(
                "from BankAccountTransaction xaction where xaction.bankAccountId = :bankAccountId and xaction.transactionType = :transactionType", paramNames, values);
        
        BigDecimal total = new BigDecimal(0);
        
        if (transactions != null) {
        	for (int i=0; i<transactions.size(); i++) {
        		BankAccountTransaction xaction = (BankAccountTransaction) transactions.get(i);
        		total = total.add(xaction.getAmount());
        	}
        }
        
        return total;
	}

	@Override
	public BankAccountTransaction findBankAccountOpeningBalanceTransaction(String bankAccountId) {
		String[] paramNames = new String[]{"bankAccountId", "transactionType"};
        
        Object[] values = new Object[]{Long.parseLong(bankAccountId), BankTransactionType.OPENING_BALANCE};
        List transactions = getHibernateTemplate().findByNamedParam(
                "from BankAccountTransaction xaction where xaction.bankAccountId = :bankAccountId and xaction.transactionType = :transactionType", paramNames, values);
                        
        if (transactions != null) {
        	for (int i=0; i<transactions.size(); i++) {
        		BankAccountTransaction xaction = (BankAccountTransaction) transactions.get(i);
        		return xaction;
        	}
        }
        
        return null;
	}
}

