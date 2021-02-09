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
import trackit.businessobjects.ExpenseType;
import trackit.dao.ExpenseTypeDao;

/**
 *
 * @author Owner
 */
public class ExpenseTypeDaoHibernateImpl extends HibernateDaoSupport
        implements ExpenseTypeDao {
    /**
     * Default constructor.
     */
    public ExpenseTypeDaoHibernateImpl() {
            super();
    }

    /**
     * @see UserDao#getUser(String)
     */
    public ExpenseType getExpenseType(String id) {
            ExpenseType type = (ExpenseType)getHibernateTemplate().load(ExpenseType.class, id);
            return type;
    }

    public ExpenseType saveExpenseType(ExpenseType type) {
        getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_ALWAYS);
        if (type.getId() == null || type.getId() == 0) {
            this.getHibernateTemplate().save(type);
        } else {
            this.getHibernateTemplate().update(type);
        }
        getHibernateTemplate().flush();
        return type;
    }

    public List findExpenseTypeByDescription(String description) {
        return getHibernateTemplate().find("from ExpenseType card where card.description=?", description);
    }

    public List<ExpenseType> getAllExpenseTypesForAccount(String accountId) {
        List accounts = getHibernateTemplate().find(
                "from Account account where account.id=?", Long.valueOf(accountId));
        Account account = (Account) accounts.get(0);
        return new ArrayList<ExpenseType>(account.getExpenseTypes());
    }
}