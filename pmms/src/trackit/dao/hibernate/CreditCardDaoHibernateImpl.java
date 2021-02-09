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
import trackit.businessobjects.CreditCard;
import trackit.businessobjects.CreditCardTransactionType;
import trackit.dao.CreditCardDao;

/**
 *
 * @author Owner
 */
public class CreditCardDaoHibernateImpl extends HibernateDaoSupport
        implements CreditCardDao {
    /**
     * Default constructor.
     */
    public CreditCardDaoHibernateImpl() {
            super();
    }

    /**
     * @see UserDao#getUser(String)
     */
    public CreditCard getCreditCard(String id) {
            CreditCard account = (CreditCard)getHibernateTemplate().load(CreditCard.class, Long.parseLong(id));
            return account;
    }

    public CreditCard saveCreditCard(CreditCard card) {
        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        if (card.getId() == null || card.getId() == 0) {
            this.getHibernateTemplate().save(card);
        } else {
            this.getHibernateTemplate().update(card);
        }
        getHibernateTemplate().flush();
        return card;
    }

    public List findCreditCardByDescription(String description) {
        return getHibernateTemplate().find("from CreditCard card where card.description=?", description);
    }

    public List<CreditCard> getAllCreditCardsForAccount(String accountId) {
        List accounts = getHibernateTemplate().find(
                "from Account account where account.id=?", Long.valueOf(accountId));
        Account account = (Account) accounts.get(0);
        return new ArrayList<CreditCard>(account.getCreditCards());
    }
    
    @Override
	public void deleteCreditCard(String id) {
		CreditCard ba = getCreditCard(id);
		getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        getHibernateTemplate().delete(ba);
        getHibernateTemplate().flush();
		
	}
	
	public int getNumberOfExpensesWithCreditCard(String id) {
		String hql="select count(expense) from Expense expense where expense.creditCardId = " + id;
		return getCountForHQL(hql);		
	}
	
	public int getNumberOfLoansWithCreditCard(String id) {
		String hql="select count(loan) from Loan loan where loan.creditCardId = " + id;
		return getCountForHQL(hql);
	}
	
	public int getNumberOfPaymentsOnCreditCard(String id) {
		String hql="select count(payment) from CreditCardPayment payment where payment.creditCardId = " + id;
		return getCountForHQL(hql);
	}
	
	public int getNumberOfNonOpeningBalanceTransactionsWithCreditCard(String id) {
		String hql="select count(xaction) from CreditCardTransaction xaction where xaction.creditCardId = " 
			+ id + " and xaction.transactionType <> " + CreditCardTransactionType.OPENING_BALANCE.toInt();
		return getCountForHQL(hql);
	}
	
	private int getCountForHQL(String hql) {
		List result = getHibernateTemplate().find(hql);
		Long count = (Long) result.get(0);
		return count.intValue();
	}
}

