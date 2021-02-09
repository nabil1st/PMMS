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
import trackit.businessobjects.CreditCardPayment;
import trackit.businessobjects.CreditCardTransaction;
import trackit.businessobjects.CreditCardTransactionType;
import trackit.dao.BankAccountTransactionDao;
import trackit.dao.CreditCardTransactionDao;

/**
 *
 * @author Owner
 */
public class CreditCardTransactionDaoHibernateImpl
        extends HibernateDaoSupport
        implements CreditCardTransactionDao {
    /**
     * Default constructor.
     */
    public CreditCardTransactionDaoHibernateImpl() {
            super();
    }

    /**
     * @see UserDao#getUser(String)
     */
    public CreditCardTransaction getCreditCardTransaction(String id) {
            CreditCardTransaction account =
                    (CreditCardTransaction)getHibernateTemplate().load(
                    CreditCardTransaction.class, Long.parseLong(id));
            return account;
    }

    public CreditCardTransaction saveCreditCardTransaction(
            CreditCardTransaction transaction) {

        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);

        if (transaction.getId() == null || transaction.getId() == 0) {
            this.getHibernateTemplate().save(transaction);
        } else {
            this.getHibernateTemplate().update(transaction);
        }
        getHibernateTemplate().flush();
        return transaction;
    }

    public CreditCardPayment getCreditCardPayment(String id) {
        CreditCardPayment payment =
                (CreditCardPayment)getHibernateTemplate().load(
                    CreditCardPayment.class, id);
        return payment;
    }

    public CreditCardPayment saveCreditCardPayment(CreditCardPayment payment) {
        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);

        if (payment.getId() == null || payment.getId() == 0) {
            this.getHibernateTemplate().save(payment);
        } else {
            this.getHibernateTemplate().update(payment);
        }
        getHibernateTemplate().flush();
        return payment;
    }

    public List<CreditCardTransaction> getAllCreditCardTransactionsForCreditCard(
            String creditCardId, Date fromDate, Date toDate) {
        String[] paramNames = new String[]{"creditCardId", "fromDate", "toDate"};
        Object[] values = new Object[]{Long.parseLong(creditCardId), fromDate, toDate};
        List transactions = getHibernateTemplate().findByNamedParam(
                "from CreditCardTransaction xaction where xaction.creditCardId = :creditCardId and xaction.date >= :fromDate and xaction.date < :toDate order by xaction.date", paramNames, values);
        return new ArrayList<CreditCardTransaction>(transactions);
    }
    
    
    public void deleteAllOpeningBalanceTransactions(String creditCardId) {
        String[] paramNames = new String[]{"creditCardId", "transactionType"};
        
        Object[] values = new Object[]{Long.parseLong(creditCardId), CreditCardTransactionType.OPENING_BALANCE};
        List transactions = getHibernateTemplate().findByNamedParam(
                "from CreditCardTransaction xaction where xaction.creditCardId = :creditCardId and xaction.transactionType = :transactionType", paramNames, values);
        
        if (transactions != null) {
        	for (int i=0; i<transactions.size(); i++) {
        		CreditCardTransaction xaction = (CreditCardTransaction) transactions.get(i);
        		getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
                getHibernateTemplate().delete(xaction);
                getHibernateTemplate().flush();
        	}
        }
    }

	@Override
	public void deleteTransaction(String id) {
		CreditCardTransaction xaction = getCreditCardTransaction(id);
		getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        getHibernateTemplate().delete(xaction);
        getHibernateTemplate().flush();
	}
	
	public BigDecimal getOpeningBalance(String creditCardId) {
		String[] paramNames = new String[]{"creditCardId", "transactionType"};
        
        Object[] values = new Object[]{Long.parseLong(creditCardId), CreditCardTransactionType.OPENING_BALANCE};
        List transactions = getHibernateTemplate().findByNamedParam(
                "from CreditCardTransaction xaction where xaction.creditCardId = :creditCardId and xaction.transactionType = :transactionType", paramNames, values);
        
        BigDecimal total = new BigDecimal(0);
        
        if (transactions != null) {
        	for (int i=0; i<transactions.size(); i++) {
        		CreditCardTransaction xaction = (CreditCardTransaction) transactions.get(i);
        		total = total.add(xaction.getAmount());
        	}
        }
        
        return total;
	}

	@Override
	public CreditCardTransaction findCreditCardOpeningBalanceTransaction(String creditCardId) {
		String[] paramNames = new String[]{"creditCardId", "transactionType"};
        
        Object[] values = new Object[]{Long.parseLong(creditCardId), CreditCardTransactionType.OPENING_BALANCE};
        List transactions = getHibernateTemplate().findByNamedParam(
                "from CreditCardTransaction xaction where xaction.creditCardId = :creditCardId and xaction.transactionType = :transactionType", paramNames, values);
                        
        if (transactions != null) {
        	for (int i=0; i<transactions.size(); i++) {
        		CreditCardTransaction xaction = (CreditCardTransaction) transactions.get(i);
        		return xaction;
        	}
        }
        
        return null;
	}
}

