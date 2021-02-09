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
import trackit.dao.ExpenseGroupDao;

/**
 *
 * @author Owner
 */
public class ExpenseGroupDaoHibernateImpl extends HibernateDaoSupport
        implements ExpenseGroupDao {
    /**
     * Default constructor.
     */
    public ExpenseGroupDaoHibernateImpl() {
            super();
    }

    /**
     * @see UserDao#getUser(String)
     */
    public ExpenseGroup getExpenseGroup(String id) {
            ExpenseGroup account = (ExpenseGroup)getHibernateTemplate().load(ExpenseGroup.class, Long.parseLong(id));
            return account;
    }

    public ExpenseGroup saveExpenseGroup(ExpenseGroup card) {
        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        if (card.getId() == null || card.getId() == 0) {
            this.getHibernateTemplate().save(card);
        } else {
            this.getHibernateTemplate().update(card);
        }
        getHibernateTemplate().flush();
        return card;
    }

    public List findExpenseGroupByDescription(String description) {
        return getHibernateTemplate().find("from ExpenseGroup card where card.description=?", description);
    }

    public List<ExpenseGroup> getAllExpenseGroupsForAccount(String accountId) {
        List accounts = getHibernateTemplate().find(
                "from Account account where account.id=?", Long.valueOf(accountId));
        Account account = (Account) accounts.get(0);
        return new ArrayList<ExpenseGroup>(account.getExpenseGroups());
    }

	@Override
	public void deleteExpenseGroup(String id) {
		ExpenseGroup eg = getExpenseGroup(id);
		getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        getHibernateTemplate().delete(eg);
        getHibernateTemplate().flush();
		
	}
	
	public int getNumberOfExpensesWithExpenseGroup(String id) {
		String hql="select count(expense) from Expense expense where expense.expenseGroupId = " + id;
		List result = getHibernateTemplate().find(hql);
		Long count = (Long) result.get(0);
		return count.intValue();
	}
	
	public int getNumberOfExpenseItemsWithExpenseGroup(String id) {
		String hql="select count(expense) from ExpenseItem expense where expense.expenseGroupId = " + id;
		List result = getHibernateTemplate().find(hql);
		Long count = (Long) result.get(0);
		return count.intValue();
	}
	
	public int getNumberOfIncomesWithExpenseGroup(String id) {
		String hql="select count(i) from Income i where i.expenseGroupId = " + id;
		List result = getHibernateTemplate().find(hql);
		Long count = (Long) result.get(0);
		return count.intValue();
	}
	
	public int getNumberOfLoansWithExpenseGroup(String id) {
		String hql="select count(loan) from Loan loan where loan.groupId = " + id;
		List result = getHibernateTemplate().find(hql);
		Long count = (Long) result.get(0);
		return count.intValue();
	}
	
	public int getNumberOfCurrencyOnHandWithExpenseGroup(String id) {
		String hql="select count(c) from CurrencyOnHand c where c.groupId = " + id;
		List result = getHibernateTemplate().find(hql);
		Long count = (Long) result.get(0);
		return count.intValue();
	}
}