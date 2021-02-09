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
import trackit.businessobjects.ExpenseGroup;
import trackit.businessobjects.Payee;
import trackit.dao.PayeeDao;

/**
 *
 * @author Owner
 */
public class PayeeDaoHibernateImpl extends HibernateDaoSupport
        implements PayeeDao {
    /**
     * Default constructor.
     */
    public PayeeDaoHibernateImpl() {
            super();
    }

    /**
     * @see UserDao#getUser(String)
     */
    public Payee getPayee(String id) {
        Payee type = (Payee)getHibernateTemplate().load(Payee.class, Long.parseLong(id));
        return type;
    }

    public Payee savePayee(Payee type) {
        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        if (type.getId() == null || type.getId() == 0) {
            this.getHibernateTemplate().save(type);
        } else {
            this.getHibernateTemplate().update(type);
        }
        getHibernateTemplate().flush();
        return type;
    }

    public List findPayeeByDescription(String description) {
        return getHibernateTemplate().find("from Payee payee where payee.description=?", description);
    }

    public List<Payee> getAllPayeesForAccount(String accountId) {
        List accounts = getHibernateTemplate().find(
                "from Account account where account.id=?", Long.valueOf(accountId));
        Account account = (Account) accounts.get(0);
        return new ArrayList<Payee>(account.getPayees());
    }
    
    @Override
	public void deletePayee(String id) {
		Payee p = getPayee(id);
		getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        getHibernateTemplate().delete(p);
        getHibernateTemplate().flush();
		
	}
	
	public int getNumberOfExpensesWithPayee(String id) {
		String hql="select count(expense) from Expense expense where expense.payeeId = " + id;
		List result = getHibernateTemplate().find(hql);
		Long count = (Long) result.get(0);
		return count.intValue();
	}
	
	public int getNumberOfLoansWithPayee(String id) {
		String hql="select count(loan) from Loan loan where loan.payeeId = " + id;
		List result = getHibernateTemplate().find(hql);
		Long count = (Long) result.get(0);
		return count.intValue();
	}
}