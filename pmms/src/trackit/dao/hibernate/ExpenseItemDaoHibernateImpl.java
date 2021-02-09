/*
 * JCatalog Project
 */
package trackit.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
//
import trackit.businessobjects.Expense;
import trackit.businessobjects.ExpenseItem;
import trackit.dao.ExpenseItemDao;
import trackit.dao.UserDao;

/**
 * The Hibernate implementation of the <code>UserDao</code>.
 *
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 * @see UserDao
 */
public class ExpenseItemDaoHibernateImpl extends HibernateDaoSupport
        implements ExpenseItemDao {
    /**
     * Default constructor.
     */
    public ExpenseItemDaoHibernateImpl() {
            super();
    }

    /**
     * @see UserDao#getUser(String)
     */
    public ExpenseItem getExpenseItem(String id) {
            ExpenseItem ex = (ExpenseItem)getHibernateTemplate().load(ExpenseItem.class, id);
            return ex;
    }

    public ExpenseItem saveExpenseItem(ExpenseItem ex) {
        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        if (ex.getId() == null || ex.getId() == 0) {
            this.getHibernateTemplate().save(ex);
        } else {
            this.getHibernateTemplate().update(ex);
        }
        getHibernateTemplate().flush();
        return ex;
    }

    public List<ExpenseItem> getAllExpenseItemsForExpense(String expenseId) {
        List expenses = getHibernateTemplate().find("from Expense expense where expense.id=?", Long.valueOf(expenseId));
        Expense expense = (Expense) expenses.get(0);
        return new ArrayList<ExpenseItem>(expense.getExpenseItems());
    }
    
    public void delete(ExpenseItem item) {
        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        getHibernateTemplate().delete(item);
        getHibernateTemplate().flush();
    }   
    
}